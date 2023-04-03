package com.sampa.springapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sampa.springapi.model.User;
import com.sampa.springapi.repository.UserRepository;
import com.sampa.springapi.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@PostAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserByid(id);
	}
	
	@PostMapping("/users/new")
	public User createUser(@RequestBody User user) {
		System.out.println("==========" + user.getUsername() + "===============");
		return userService.createUser(user);
	}
	
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable Long id, @RequestBody User userDetails) {
		User  user = userService.updateUserById(id, userDetails);
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		user.setPassword(userDetails.getPassword());
		
		return userService.createUser(user);
	}
	
	@DeleteMapping("/users/{id}")
	public String deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		return "User deleted successfully.";
	}
}
