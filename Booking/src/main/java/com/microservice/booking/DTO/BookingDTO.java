package com.microservice.booking.DTO;

import com.microservice.booking.entity.Booking.BookingStatus;

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
	private BookingStatus status;
	
	
}
