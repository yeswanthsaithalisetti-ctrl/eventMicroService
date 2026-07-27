package com.Microservice.Payment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

	private Long bookingId;
	private double amount;
	private Long event;
	private Long user;
	private String status;
}
