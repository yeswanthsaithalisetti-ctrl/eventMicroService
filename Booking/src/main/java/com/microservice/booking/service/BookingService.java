package com.microservice.booking.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservice.booking.entity.Booking;
import com.microservice.booking.repo.BookingRepo;

@Service
public class BookingService {

	@Autowired
	private BookingRepo bookingRepo;
	
	public ResponseEntity<String> createOrder(Booking booking) {
		
		String event=null;
		//Event event = eventRepo.findById(booking.getEvent().getId()).orElse(null);
		if(event!=null) {
			if(event=="")// .getSeatsAvailable()
			{
				//event.setSeatsAvailable(event.getSeatsAvailable()-booking.getTotalBookings());
				//eventService.updateEvent(event);
				booking.setBookingDate(new Date());
				bookingRepo.save(booking);
			
				return new ResponseEntity<String>("Booking Confirmed",HttpStatus.CREATED);
			}else {
				booking.setStatus(Booking.BookingStatus.FAILED);
				bookingRepo.save(booking);
				return new ResponseEntity<String>("Booking Failed, Seats Not available.",HttpStatus.CONFLICT);
			}
		}
		else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<Booking>> getBookings(Long user) {
		if(user!=null) {
			List<Booking> bookings = bookingRepo.findByUser(user);
			if(bookings!=null && !bookings.isEmpty()) 
				return new ResponseEntity<List<Booking>>(bookings,HttpStatus.OK);
			else
				return new ResponseEntity<List<Booking>>(HttpStatus.NOT_FOUND);
		}
		else
			return new ResponseEntity<List<Booking>>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<List<Booking>> getEventBookings(Long user, Long event) {
		if(user!=null && event!=null)
			return new ResponseEntity<List<Booking>>(bookingRepo.findByUserAndEvent(user,event),HttpStatus.OK);
		else
			return new ResponseEntity<List<Booking>>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<Booking> getBooking(Long id) {
		Booking booking=bookingRepo.findById(id).orElse(null);
		if(booking!=null)
			return new ResponseEntity<Booking>(booking,HttpStatus.OK);
		else
			return new ResponseEntity<Booking>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> updateBooking(Booking booking) {
		bookingRepo.save(booking);
		
		return new ResponseEntity<String>("Booking Updated",HttpStatus.OK);
	}

	public ResponseEntity<String> deleteBooking(Long id) {
		bookingRepo.deleteById(id);
		
		return new ResponseEntity<String>("Booking Deleted",HttpStatus.OK);
	}
	
	

}
