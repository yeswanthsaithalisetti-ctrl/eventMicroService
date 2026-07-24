package com.microservice.user.Controller;

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

import com.microservice.user.Entity.User;
import com.microservice.user.Service.UserService;




@RestController
@RequestMapping("user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("register")
	public ResponseEntity<String> createUser(@RequestBody User user){
		
		ResponseEntity<String> id = userService.createUser(user);
		
		return id;
	}
	
	@GetMapping()
	public ResponseEntity<List<User>>getUsers() {
		return userService.getUsers();
	}
	
	@PutMapping("updateUser")
	public ResponseEntity<String> updateUser(@RequestBody User user){
		return userService.update(user);
	}
	
	@DeleteMapping("deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		return userService.delete(id);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		return userService.getUser(id);
	}

}
