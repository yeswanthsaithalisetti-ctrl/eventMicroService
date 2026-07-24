package com.microservice.booking.repo;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.booking.entity.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {

	List<Booking> findByUser(Long orgUser);

	@Nullable
	List<Booking> findByUserAndEvent(Long orgUser, Long orgEvent);

}
