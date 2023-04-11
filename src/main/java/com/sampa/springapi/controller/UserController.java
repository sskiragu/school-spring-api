package com.sampa.springapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sampa.springapi.UserInfoUserDetails;
import com.sampa.springapi.model.User;
import com.sampa.springapi.repository.UserRepository;
import com.sampa.springapi.service.JwtService;
import com.sampa.springapi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@PostAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Long id, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		System.out.println("============================" + token + "===========================");
		Integer userId = jwtService.extractUserId(token);
		
		System.out.println("============================" + userId + "===========================");
	    if (!userId.equals(id.intValue())) {
	        throw new AccessDeniedException("User not authorized to access this resource");
	    }
	    return userService.getUserByid(id);
		      
	}
	
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PostAuthorize("hasRole('ROLE_STUDENT')")
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
