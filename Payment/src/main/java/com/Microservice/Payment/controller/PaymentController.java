package com.Microservice.Payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Microservice.Payment.DTO.PaymentDTO;
import com.Microservice.Payment.service.PaymentService;




@RestController
@RequestMapping("payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	
	@PostMapping("{bookingId}")
	public ResponseEntity<String> createPayment(@PathVariable Long bookingId)
	{
		return paymentService.createPayment(bookingId);
	}

	@PostMapping("verifyPayment")
	public ResponseEntity<String> verify(@RequestBody PaymentDTO paymentDTO)
	{
		return paymentService.verify(paymentDTO);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<String> getStatus(@PathVariable Long id){
		return paymentService.getStatus(id);
	}
}
