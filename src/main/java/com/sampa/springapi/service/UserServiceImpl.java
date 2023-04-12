package com.sampa.springapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sampa.springapi.model.Role;
import com.sampa.springapi.model.User;
import com.sampa.springapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByid(Long id) {
		Optional<User> user = 
				userRepository.findById(id);
		return user.get();
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUserById(Long id, User userDetails) {
		User user = userRepository.findById(id).get();
		if(userDetails.getUsername() != null) {
			user.setUsername(userDetails.getUsername());
		}
		if(userDetails.getEmail() != null) {
			user.setEmail(userDetails.getEmail());
		}
		if(userDetails.getPassword() != null) {
			user.setPassword(userDetails.getPassword());
		}
		
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

}
