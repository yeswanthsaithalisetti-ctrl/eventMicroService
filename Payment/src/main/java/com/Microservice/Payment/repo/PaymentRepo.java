package com.Microservice.Payment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Microservice.Payment.entity.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{

	Payment findByRazorpayOrderId(String razorpayOrderId);

}
