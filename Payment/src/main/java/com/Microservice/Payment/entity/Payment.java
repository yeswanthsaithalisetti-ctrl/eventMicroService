package com.Microservice.Payment.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long booking;

	private Double amount;

	private String razorpayOrderId;

	private String razorpayPaymentId;

	private String razorpaySignature;

	@Enumerated(EnumType.STRING)
	private PaymentStatus status = PaymentStatus.PENDING;

	private Date createdAt;

	public enum PaymentStatus {
		PENDING, SUCCESS, FAILED, REFUNDED
	}

}
