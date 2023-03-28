package com.sampa.springapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampa.springapi.model.Role;
import com.sampa.springapi.model.User;
import com.sampa.springapi.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	Optional<UserRole> findByUserAndRole(User user, Role role);
}
