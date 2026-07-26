package com.microservice.event.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.microservice.event.DTO.EventDTO;
import com.microservice.event.entity.Event;
import com.microservice.event.service.EventService;

@RestController
@RequestMapping("event")
public class EventController {

	@Autowired
	private EventService eventService;
	
	
	@PostMapping()
	public ResponseEntity<String> createEvent(@RequestBody Event event){
		return eventService.createEvent(event);
	}
	
	
	@GetMapping()
	public ResponseEntity<List<Event>> getEvents(){
		return eventService.getEvents();
	}
	
	@GetMapping("category/{cat}")
	public ResponseEntity<List<Event>> getEventsByCat(@PathVariable String cat){
		return eventService.getCatEvent(cat);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Event> getEvent(@PathVariable Long id){
		return eventService.getEvent(id);
	}
	
	@PutMapping("updateEvent")
	public ResponseEntity<String> updateEvent(@RequestBody Event event){
		return eventService.updateEvent(event);
	}
	
	@DeleteMapping("deleteEvent/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long id){
		return eventService.deleteEvent(id);
	}
	
	@GetMapping("inventory/{id}")
	public ResponseEntity<EventDTO> getSeats(@PathVariable Long id){
		return eventService.getSeats(id);
	}
	
	@GetMapping("seatsUpdate/{id}/{seats}")
	public ResponseEntity<String> updateSeats(@PathVariable Long id,@PathVariable int seats)
	{
		return eventService.updateSeats(id,seats);
	}
	
}
