package com.sampa.springapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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
import com.sampa.springapi.repository.UserRepository;
import com.sampa.springapi.service.RoleService;
import com.sampa.springapi.service.UserService;

@RestController
@PostAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/roles")
	public List<Role> getRoles() {
		return roleService.getAllRoles();
	}
	
	@GetMapping("/roles/{id}")
	public Role getRoleById(@PathVariable Long id) {
		return roleService.getRoleByid(id);
	}
	
	@PostMapping("/roles/new")
	public Role createRole(@RequestBody Role role) {
		return roleService.createRole(role);
	}
	
	@PutMapping("/roles/{id}")
	public Role updateRoleById(@PathVariable Long id, @RequestBody Role roleDetails) {
		Role  role = roleService.updateRoleById(id, roleDetails);
		role.setName(roleDetails.getName());
		
		return roleService.createRole(role);
	}
	
	@DeleteMapping("/roles/{id}")
	public String deleteRoleById(@PathVariable Long id) {
		roleService.deleteRoleById(id);
		return "Role deleted successfully.";
	}
}
