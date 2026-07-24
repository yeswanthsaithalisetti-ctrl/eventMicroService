package com.microservice.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.booking.entity.Booking;
import com.microservice.booking.service.BookingService;

@RestController
@RequestMapping("order")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;

	@PostMapping()
	public ResponseEntity<String> placeOrder(@RequestBody Booking booking){
		return bookingService.createOrder(booking);
	}
	
	@GetMapping("{user}")
	public ResponseEntity<List<Booking>> getBookings(@PathVariable Long user){
		return bookingService.getBookings(user);
	}
	
	@GetMapping("{user}/{event}")
	public ResponseEntity<List<Booking>> getBookingsByCat(@PathVariable Long user,@PathVariable Long event){
		return bookingService.getEventBookings(user,event);
	}
	
	@GetMapping("Booking/{id}")
	public ResponseEntity<Booking> getBooking(@PathVariable Long id){
		return bookingService.getBooking(id);
	}
	
	@PutMapping("updateBooking")
	public ResponseEntity<String> updateBooking(@RequestBody Booking Booking){
		return bookingService.updateBooking(Booking);
	}
	
	@DeleteMapping("deleteBooking/{id}")
	public ResponseEntity<String> deleteBooking(@PathVariable Long id){
		return bookingService.deleteBooking(id);
	}
}
