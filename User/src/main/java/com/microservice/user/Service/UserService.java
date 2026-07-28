package com.microservice.user.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.user.DTO.UserDTO;
import com.microservice.user.Entity.User;
import com.microservice.user.Repo.UserRepo;



@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public ResponseEntity<String> createUser(User user) {
		
		String name = user.getName().substring(0, 4);
		String num = user.getPhoneNumber().toString().substring(6, 10);
		String userId = name+"_"+num;
		user.setUserId(userId);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		String message = "User created with UserId : "+userId;
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userRepo.findAll();
		if(users!=null && !users.isEmpty())
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		else
			return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> update(User user) {
		
		userRepo.save(user);
		
		return new ResponseEntity<String>("User Updated.", HttpStatus.OK);
	}

	public ResponseEntity<String> delete(Long id) {
		userRepo.deleteById(id);
		return new ResponseEntity<String>("Succesfully deleted.",HttpStatus.OK);
	}

	public ResponseEntity<User> getUser(Long id) {
		User user = userRepo.findById(id).orElse(null);
		if(user!=null)
			return new ResponseEntity<User>(user,HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<UserDTO> getDetails(Long id) {
		User user = userRepo.findById(id).orElse(null);
		if(user!=null) {
			UserDTO details = new UserDTO();
			details.setUserId(user.getId());
			details.setUserName(user.getName());
			details.setEmail(user.getEmail());
			return new ResponseEntity<UserDTO>(details,HttpStatus.OK);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
