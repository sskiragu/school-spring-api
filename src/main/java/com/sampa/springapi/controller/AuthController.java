package com.sampa.springapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sampa.springapi.model.User;
import com.sampa.springapi.service.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
//	@PostMapping("/login")
//	public String login(@RequestBody Map<String, String> request) {
//		String username = request.get("username");
//		String password = request.get("password");
//		return username + " " + password;
//	}
	
	@PostMapping("/signup")
	public User signup(@RequestBody User user) {
		System.out.println("Testing here" + user);
		return authService.signup(user);
	}
}
