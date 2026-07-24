package com.microservice.event.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private String venue;

	private Date eventDate;

	private int seatsAvailable;

	private int noOfSeats;

	private Double price;

	private String category;

	private Long createdBy;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

}
