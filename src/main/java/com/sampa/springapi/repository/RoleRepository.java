package com.sampa.springapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sampa.springapi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
