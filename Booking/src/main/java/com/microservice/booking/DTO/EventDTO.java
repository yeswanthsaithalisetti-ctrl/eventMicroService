package com.microservice.booking.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventDTO {
	
	private Long eventId;
	
	private int noSeats;

	
}
