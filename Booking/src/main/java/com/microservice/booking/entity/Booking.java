package com.microservice.booking.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long user;
	
	@NotNull
	private Long event;
	
	private int totalBookings;
	
	private Double totalAmount;
	
	private Date bookingDate;
	
	@Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;
	
	public enum BookingStatus { PENDING, CONFIRMED, CANCELLED,FAILED }

}
