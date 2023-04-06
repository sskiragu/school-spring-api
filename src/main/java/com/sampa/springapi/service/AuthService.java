package com.sampa.springapi.service;

import com.sampa.springapi.dto.AuthRequest;
import com.sampa.springapi.model.User;

public interface AuthService {
	
	public String login(AuthRequest authRequest);
	public User signup(User user);
	
}
