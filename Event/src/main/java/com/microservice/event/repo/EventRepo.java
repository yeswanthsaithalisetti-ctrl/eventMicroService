package com.microservice.event.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.event.entity.Event;

public interface EventRepo extends JpaRepository<Event, Long> {

	List<Event> findByCategory(String cat);

}
