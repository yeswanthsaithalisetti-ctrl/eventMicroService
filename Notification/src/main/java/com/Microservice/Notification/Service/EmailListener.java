package com.Microservice.Notification.Service;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Microservice.Notification.DTO.EmailDTO;
import com.Microservice.Notification.config.RabbitMQConfig;



@Service
public class EmailListener {

	@Value("${spring.mail.username}")
    private String fromEmail;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RabbitListener(queues = RabbitMQConfig.emailQueue )
	public void confirmMail(EmailDTO email) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		String messageText = String.format(
	            "Hi %s,%n%n" +
	                    "Your booking for \"%s\" has been confirmed!%n%n" +
	                    "Booking ID: %d%n" +
	                    "Amount Paid: ₹%.2f%n" +
	                    "Payment ID: %s%n%n" +
	                    "Thank you for booking with us!%n",
	                    email.getUserName(),
	                    email.getEventName(),
	                    email.getBookingId(),
	                    email.getAmountPaid(),
	                    email.getRazorpayPaymentId()
	                );
		
		message.setFrom(fromEmail);
		message.setTo(email.getUserEmail());
		message.setSubject("Booking Confirmation for "+email.getEventName());
		message.setText(messageText);
		mailSender.send(message);
		System.out.println("email sent");
	}
	
	
}

