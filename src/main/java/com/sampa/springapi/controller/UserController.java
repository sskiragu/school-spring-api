package com.sampa.springapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sampa.springapi.model.User;
import com.sampa.springapi.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return user;
	}
	
	@PutMapping("/users/{id}")
	public Long updateUserById(@PathVariable Long id) {
		return id;
	}
	
	@DeleteMapping("/users/{id}")
	public Integer deleteUserById(@PathVariable Integer id) {
		return id;
	}
}
