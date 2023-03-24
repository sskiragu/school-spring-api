package com.sampa.springapi.repository;


import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.sampa.springapi.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

}
