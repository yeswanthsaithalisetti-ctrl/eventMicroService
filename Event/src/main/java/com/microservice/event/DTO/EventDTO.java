package com.microservice.event.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDTO {
	
	private Long eventId;
	
	private int noSeats;

	
}
