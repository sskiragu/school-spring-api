package com.sampa.springapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sampa.springapi.dto.AuthRequest;
import com.sampa.springapi.model.User;
import com.sampa.springapi.service.AuthService;
import com.sampa.springapi.service.JwtService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public String login(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		}else {
			throw new UsernameNotFoundException("Invalid credeentials");
		}
	}
	
	@PostMapping("/signup")
	public User signup(@RequestBody User user) {
		System.out.println("Testing here" + user);
		return authService.signup(user);
	}
}
