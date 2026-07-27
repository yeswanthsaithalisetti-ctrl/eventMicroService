package com.Microservice.Payment.service;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.Microservice.Payment.DTO.BookingDTO;
import com.Microservice.Payment.DTO.EmailDTO;
import com.Microservice.Payment.DTO.EventDTO;
import com.Microservice.Payment.DTO.PaymentDTO;
import com.Microservice.Payment.DTO.UserDTO;
import com.Microservice.Payment.config.RabbitMQConfig;
import com.Microservice.Payment.entity.Payment;
import com.Microservice.Payment.repo.PaymentRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {

	@Value("${razorpay.key-id}")
	private String keyId;

	@Value("${razorpay.key-secret}")
	private String keySecret;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private RazorpayClient razorpayClient;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public ResponseEntity<String> createPayment(Long bookingId) {

		BookingDTO booking = new RestTemplate().getForEntity("http://localhost:8083/order/details/"+bookingId, BookingDTO.class).getBody();

		if (booking != null) {
			int amount = (int) (booking.getAmount() * 100);

			JSONObject order = new JSONObject();

			order.put("amount", amount);
			order.put("currency", "INR");
			order.put("receipt", "booking_" + bookingId);

			try {
				Order payOrder = razorpayClient.orders.create(order);

				Payment payment = new Payment();
				payment.setAmount(booking.getAmount());
				payment.setBooking(booking.getBookingId());
				payment.setRazorpayOrderId(payOrder.get("id"));
				payment.setStatus(Payment.PaymentStatus.PENDING);
				payment.setCreatedAt(new Date());
				paymentRepo.save(payment);

				return new ResponseEntity<String>("Order Id : " + payOrder.get("id"), HttpStatus.CREATED);
			} catch (RazorpayException e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		} else
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> verify(PaymentDTO paymentDTO) {

		String sign = paymentDTO.getRazorpayOrderId() + "|" + paymentDTO.getRazorpayPaymentId();

		Payment payment = paymentRepo.findByRazorpayOrderId(paymentDTO.getRazorpayOrderId());
		BookingDTO booking = new RestTemplate().getForEntity("http://localhost:8083/order/details/"+paymentDTO.getBookingId(), BookingDTO.class)
				.getBody();

		String genSign = "";
		try {
			genSign = generateHmac(sign, keySecret);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (genSign.equalsIgnoreCase(paymentDTO.getRazorpaySignature())) {
			payment.setStatus(Payment.PaymentStatus.SUCCESS);
			payment.setRazorpayPaymentId(paymentDTO.getRazorpayPaymentId());
			payment.setRazorpaySignature(genSign);

			booking.setStatus("CONFIRMED");
			UserDTO user = new RestTemplate().getForEntity("http://localhost:8085/user/getdetails/"+booking.getUser(),UserDTO.class)
					.getBody();
			EventDTO event = new RestTemplate().getForEntity("http://localhost:8082/event/inventory/"+booking.getEvent(),EventDTO.class)
					.getBody();
			EmailDTO email = new EmailDTO();
			email.setBookingId(booking.getBookingId());
			email.setEventName(event.getTitle());
			email.setAmountPaid(payment.getAmount());
			email.setRazorpayPaymentId(payment.getRazorpayPaymentId());
			email.setUserEmail(user.getEmail());
			email.setUserName(user.getUserName());
			rabbitTemplate.convertAndSend(RabbitMQConfig.emailExchange, RabbitMQConfig.emailRoutingKey, email);

			paymentRepo.save(payment);
			String res=new RestTemplate().getForEntity("http://localhost:8083/order/updateStatus/"+booking.getBookingId()+"/"+booking.getStatus(), String.class)
			.getBody();

			return new ResponseEntity<String>("Booking Confirmed", HttpStatus.OK);
		} else {
			payment.setStatus(Payment.PaymentStatus.FAILED);
			booking.setStatus("FAILED");
			paymentRepo.save(payment);
			String res=new RestTemplate().getForEntity("http://localhost:8083/order/updateStatus/"+booking.getBookingId()+"/"+booking.getStatus(), String.class)
					.getBody();
			return new ResponseEntity<String>("Booking Failed", HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<String> getStatus(Long id) {
		Payment payment = paymentRepo.findById(id).orElse(null);

		if (payment != null)
			return new ResponseEntity<String>("Payment status is : " + payment.getStatus(), HttpStatus.OK);
		else
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	private String generateHmac(String data, String secret) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		mac.init(secretKey);
		byte[] hash = mac.doFinal(data.getBytes());

		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			hexString.append(String.format("%02x", b));
		}
		return hexString.toString();
	}

}
