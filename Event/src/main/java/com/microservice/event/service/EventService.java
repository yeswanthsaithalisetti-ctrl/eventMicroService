package com.microservice.event.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.microservice.event.DTO.EventDTO;
import com.microservice.event.entity.Event;
import com.microservice.event.repo.EventRepo;

@Service
public class EventService {

	@Autowired
	private EventRepo eventRepo;
	

	public ResponseEntity<String> createEvent(Event event) {
		
		event.setCreateDate(new Date());
		eventRepo.save(event);
		return new ResponseEntity<String>(event.getTitle()+" Event Created successfully.",HttpStatus.CREATED);
	}


	public ResponseEntity<List<Event>> getEvents() {
		List<Event> events=eventRepo.findAll();
		if(events!=null && !events.isEmpty())
			return new ResponseEntity<List<Event>>(events,HttpStatus.OK);
		else
			return new ResponseEntity<List<Event>>(HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<List<Event>> getCatEvent(String cat) {
		List<Event> events=eventRepo.findByCategory(cat);
		if(events!=null && !events.isEmpty())
			return new ResponseEntity<List<Event>>(events,HttpStatus.OK);
		else
			return new ResponseEntity<List<Event>>(HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<Event> getEvent(Long id) {
		Event event =eventRepo.findById(id).orElse(null);
		if(event!=null)
			return new ResponseEntity<Event>(event,HttpStatus.OK);
		else
			return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<String> updateEvent(Event event) {
		Event orgEvent = eventRepo.findById(event.getId()).orElse(null);
		
		if(event.getCategory()!=null && !event.getCategory().isEmpty())
			orgEvent.setCategory(event.getCategory());
		if(event.getDescription()!=null && !event.getDescription().isEmpty())
			orgEvent.setDescription(event.getDescription());
		if(event.getEventDate()!=null)
			orgEvent.setEventDate(event.getEventDate());
		if(event.getNoOfSeats()!=orgEvent.getNoOfSeats())
			orgEvent.setNoOfSeats(event.getNoOfSeats());
		if(event.getPrice()!=orgEvent.getPrice())
			orgEvent.setPrice(event.getPrice());
		if(event.getSeatsAvailable()!=orgEvent.getSeatsAvailable())
			orgEvent.setSeatsAvailable(event.getSeatsAvailable());
		
		eventRepo.save(orgEvent);
		return new ResponseEntity<String>("Event Updated Successfully.",HttpStatus.OK);
	}


	public ResponseEntity<String> deleteEvent(Long id) {
		eventRepo.deleteById(id);
		return new ResponseEntity<String>("Event Deleted successfully.",HttpStatus.OK);
	}
	
	public ResponseEntity<EventDTO> getSeats(Long id){
		Event event=eventRepo.findById(id).orElse(null);
		if(event!=null) {
			EventDTO response = new EventDTO();
			response.setEventId(event.getId());
			response.setNoSeats(event.getSeatsAvailable());
			return new ResponseEntity<EventDTO>(response,HttpStatus.OK);
		}
		else
			return new ResponseEntity<EventDTO>(HttpStatus.NOT_FOUND);
	}


	public ResponseEntity<String> updateSeats(Long id, int seats) {
		Event event = eventRepo.findById(id).orElse(null);
		if(event!=null) {
			event.setSeatsAvailable(event.getSeatsAvailable()-seats);
			eventRepo.save(event);
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("Event Not Found",HttpStatus.NOT_FOUND);
	}
	
}
