package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.Register;
import com.testtask.TaskManagementSystem.entity.Users;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.security.UsersDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
        return new UsersDetails(users);
    }

    public boolean userExists(String username) {
        Users userNotExists = new Users();
        Users users = usersRepository.findByUsername(username).orElse(userNotExists);
        return !userNotExists.equals(users);
    }

    public void createUser(Register register, String password) {
        Users users = new Users();
        users.setPassword(password);
        users.setUsername(register.getUsername());
        users.setRole(register.getRole());
        usersRepository.save(users);
    }
}
