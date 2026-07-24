package com.microservice.user.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.user.Entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUserId(String userId);

}
