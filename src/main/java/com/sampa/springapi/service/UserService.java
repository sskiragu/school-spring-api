package com.sampa.springapi.service;

import java.util.List;

import com.sampa.springapi.model.User;

public interface UserService {
	public List<User> getAllUsers();
	public User getUserByid(Long id);
	public User createUser(User user);
	public User updateUserById(Long id, User user);
	public void deleteUserById(Long id);
}
