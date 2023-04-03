package com.sampa.springapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sampa.springapi.model.User;
import com.sampa.springapi.repository.AuthRepository;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Override
	public User signup(User user) {
		if(authRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return authRepository.save(user);
	}

//	@Override
//	public String login() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
}
