package com.sampa.springapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sampa.springapi.model.Role;
import com.sampa.springapi.model.User;
import com.sampa.springapi.model.UserRole;
import com.sampa.springapi.repository.RoleRepository;
import com.sampa.springapi.repository.UserRepository;
import com.sampa.springapi.service.RoleService;
import com.sampa.springapi.service.UserRoleService;
import com.sampa.springapi.service.UserService;

@RestController
public class UserRoleController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/user-roles")
	public List<UserRole> getUserRoles() {
		return userRoleService.getAllUserRoles();
	}
	
	@GetMapping("/user-roles/{id}")
	public UserRole getUserRoleById(@PathVariable Long id) {
		return userRoleService.getUserRoleByid(id);
	}
	
	@PostMapping("/user-roles/new")
	public UserRole createUserRole(@RequestBody Map<String, Long> request) {
		Long userId = request.get("user_id");
	    Long roleId = request.get("role_id");

	    User user = userService.getUserByid(userId);
	    Role role = roleService.getRoleByid(roleId);

	    UserRole userRole = new UserRole(user, role);
	    return userRoleService.createUserRole(userRole);
	}
	
	@PutMapping("/user-roles/{id}")
	public UserRole updateUserRoleById(@PathVariable Long id, @RequestBody Map<String, Long> request) {
		Long userId = request.get("user_id");
		Long roleId = request.get("role_id");
		UserRole userRole = userRoleService.getUserRoleByid(id);
		User user = userService.getUserByid(userId);
		Role role = roleService.getRoleByid(roleId);
		userRole.setUser(user);
		userRole.setRole(role);

		return userRoleService.updateUserRoleById(id, userRole);
	}
	
	@DeleteMapping("/user-roles/{id}")
	public String deleteUserRoleById(@PathVariable Long id) {
		userRoleService.deleteUserRoleById(id);
		return "User Role deleted successfully.";
	}
}
