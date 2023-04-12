package com.sampa.springapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sampa.springapi.exceptions.ResponseMessage;
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
	public ResponseEntity<List<User>> getUsers() {
		try {
			List<User> users = userService.getAllUsers();
			if(users.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletRequest request) {
		try {
			String token = request.getHeader("Authorization").substring(7);
			Integer userId = jwtService.extractUserId(token);
		    if (!userId.equals(id.intValue())) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("User not authorized to access this resource"));
		    }
		    User users = userService.getUserByid(id);
		    return ResponseEntity.ok(users);
		    
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
			
		}
		      
	
	@PostAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PostAuthorize("hasRole('ROLE_STUDENT')")
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable Long id, @RequestBody User userDetails, HttpServletRequest request) {
		User  user = userService.updateUserById(id, userDetails);
		String token = request.getHeader("Authorization").substring(7);
		Integer userId = jwtService.extractUserId(token);
		if(userDetails.getUsername() != null) {
			user.setUsername(userDetails.getUsername());
		}
		if(userDetails.getEmail() != null) {
			user.setEmail(userDetails.getEmail());
		}
		if(userDetails.getPassword() != null) {
			user.setPassword(userDetails.getPassword());
		}
		
		if(!userId.equals(id.intValue())) {
			throw new AccessDeniedException("User not authorized to access this resource");
		}
		return userService.createUser(user);
	}
	
	@PostAuthorize("hasRole('ROLE_STUDENT')")
	@DeleteMapping("/users/{id}")
	public String deleteUserById(@PathVariable Long id, HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		Integer userId = jwtService.extractUserId(token);
		if(!userId.equals(id.intValue())) {
			throw new AccessDeniedException("User not authorized to access this resource");
		}
		userService.deleteUserById(id);
		return "User deleted successfully.";
	}
}
