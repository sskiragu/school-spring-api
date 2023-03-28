package com.sampa.springapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sampa.springapi.model.User;
import com.sampa.springapi.model.UserRole;
import com.sampa.springapi.repository.UserRepository;
import com.sampa.springapi.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> getAllUserRoles() {
		return userRoleRepository.findAll();
	}

	@Override
	public UserRole getUserRoleByid(Long id) {
		
		Optional<UserRole> userRole = 
				userRoleRepository.findById(id);
		return userRole.get();
	}

	@Override
	public UserRole createUserRole(UserRole userRole) {
		Optional<UserRole> existingUserRole =  userRoleRepository.findByUserAndRole(userRole.getUser(), userRole.getRole());
		
		if(existingUserRole.isPresent()) {
			throw new IllegalArgumentException("User already has this role");
		}
		return userRoleRepository.save(userRole);
	}

	@Override
	public UserRole updateUserRoleById(Long id, UserRole userRoleDetails) {
		UserRole userRole = userRoleRepository.findById(id).get();
		userRole.setUser(userRoleDetails.getUser());
		userRole.setRole(userRoleDetails.getRole());
		return userRoleRepository.save(userRole);
	}

	@Override
	public void deleteUserRoleById(Long id) {
		userRoleRepository.deleteById(id);
	}
	
}
