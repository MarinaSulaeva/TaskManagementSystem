package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс для работы с сущностью пользователя в базе данных
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
