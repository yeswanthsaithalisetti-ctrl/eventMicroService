package com.microservice.booking.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.booking.DTO.BookingDTO;
import com.microservice.booking.DTO.EventDTO;
import com.microservice.booking.entity.Booking;
import com.microservice.booking.entity.Booking.BookingStatus;
import com.microservice.booking.repo.BookingRepo;

@Service
public class BookingService {

	@Autowired
	private BookingRepo bookingRepo;
	
	public ResponseEntity<String> createOrder(Booking booking) {

		ResponseEntity<EventDTO> resEntity = new RestTemplate().getForEntity("http://localhost:8082/event/inventory/"+booking.getEvent(),EventDTO.class);
		EventDTO event=resEntity.getBody();
		if(event!=null) {
			if(event.getNoSeats()>0) {
				ResponseEntity<String> setEntity = new RestTemplate()
						.getForEntity("http://localhost:8082/event/seatsUpdate/"+booking.getEvent()+"/"+booking.getTotalBookings(),String.class);
				if( HttpStatus.OK.equals(setEntity.getStatusCode())) {
					booking.setBookingDate(new Date());
					bookingRepo.save(booking);
			
					return new ResponseEntity<String>("Booking Confirmed",HttpStatus.CREATED);
				}
				else
					return new ResponseEntity<String>(setEntity.getBody(),setEntity.getStatusCode());
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

	public ResponseEntity<BookingDTO> getAmount(Long id) {
		Booking booking=bookingRepo.findById(id).orElse(null);
		if(booking!=null) {
			BookingDTO amount = new BookingDTO();
			amount.setBookingId(booking.getId());
			amount.setAmount(booking.getTotalBookings());
			amount.setEvent(booking.getEvent());
			amount.setUser(booking.getUser());
			amount.setStatus(booking.getStatus());
			return new ResponseEntity<BookingDTO>(amount,HttpStatus.OK);
		}
		else
			return new ResponseEntity<BookingDTO>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> status(Long id, String status) {
		Booking booking = bookingRepo.findById(id).orElse(null);
		if(booking!=null) {
			booking.setStatus(BookingStatus.valueOf(status));
			bookingRepo.save(booking);
			return new ResponseEntity<String>("Booking Updated",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("Booking not found",HttpStatus.NOT_FOUND);
	}
	
	

}
