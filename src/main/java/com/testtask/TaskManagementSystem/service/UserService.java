package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.Register;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Интерфейс для работы с пользователем
 */
public interface UserService extends UserDetailsService {
    void createUser(Register register, String password);

    boolean userExists(String username);
}
