package com.sampa.springapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sampa.springapi.dto.AuthRequest;
import com.sampa.springapi.model.User;
import com.sampa.springapi.repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public String login(AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		User user = authRepository.findByUsername(authRequest.getUsername())
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername(), user.getId());
		}else {
			throw new UsernameNotFoundException("Invalid credentials");
		}
	}

	@Override
	public User signup(User user) {
		if(authRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return authRepository.save(user);
	}
	
}
