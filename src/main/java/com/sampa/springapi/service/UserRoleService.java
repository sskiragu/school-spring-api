package com.sampa.springapi.service;

import java.util.List;

import com.sampa.springapi.model.User;
import com.sampa.springapi.model.UserRole;

public interface UserRoleService {
	public List<UserRole> getAllUserRoles();
	public UserRole getUserRoleByid(Long id);
	public UserRole createUserRole(UserRole userRole);
	public UserRole updateUserRoleById(Long id, UserRole userRole);
	public void deleteUserRoleById(Long id);
}
