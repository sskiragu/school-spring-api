package com.sampa.springapi.service;

import java.util.List;

import com.sampa.springapi.model.Role;

public interface RoleService {
	public List<Role> getAllRoles();
	public Role getRoleByid(Long id);
	public Role createRole(Role role);
	public Role updateRoleById(Long id, Role role);
	public void deleteRoleById(Long id);
}
