package com.sampa.springapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampa.springapi.model.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	Optional<User> findByUsername(String username);
}
