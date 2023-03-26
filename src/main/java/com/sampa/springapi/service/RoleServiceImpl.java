package com.sampa.springapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sampa.springapi.model.Role;
import com.sampa.springapi.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRoleByid(Long id) {
		Optional<Role> role = 
				roleRepository.findById(id);
		return role.get();
	}

	@Override
	public Role createRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Role updateRoleById(Long id, Role roleDetails) {
		Role role = roleRepository.findById(id).get();
		role.setName(roleDetails.getName());
		
		return roleRepository.save(role);
	}

	@Override
	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}

}
