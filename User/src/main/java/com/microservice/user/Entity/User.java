package com.microservice.user.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String name;
	
	private String email;
	
	private Long phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private String userId;
	
	private String password;
	
	public enum Role {ADMIN,USER,ORGANISER};
}
