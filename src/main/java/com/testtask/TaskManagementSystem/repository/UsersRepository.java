package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
