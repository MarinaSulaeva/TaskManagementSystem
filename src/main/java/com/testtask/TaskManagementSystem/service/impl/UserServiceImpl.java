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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
        return new UsersDetails(user);
    }

    public boolean userExists(String username) {
        User userNotExists = new User();
        User user = usersRepository.findByUsername(username).orElse(userNotExists);
        return !userNotExists.equals(user);
    }

    public void createUser(Register register, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(register.getUsername());
        user.setRole(register.getRole());
        usersRepository.save(user);
    }
}
