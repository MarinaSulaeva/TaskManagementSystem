package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.Register;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.security.UsersDetails;
import com.testtask.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Класс-сервис создания и поиска пользователя
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    /**
     * Метод для получения UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
        return new UsersDetails(user);
    }

    /**
     * Метод для проверки существования пользователя в базе данных
     */
    @Override
    public boolean userExists(String username) {
        User user = usersRepository.findByUsername(username).orElse(null);
        return !Objects.isNull(user);
    }

    /**
     * Метод для получения пользователя по никнейму из базы данных
     */
    @Override
    public User findUserByUserName(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
    }

    /**
     * Метод для создания пользователя
     */
    public void createUser(Register register, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(register.getUsername());
        user.setRole(register.getRole());
        usersRepository.save(user);
    }
}
