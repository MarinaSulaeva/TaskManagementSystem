package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.JwtRequest;
import com.testtask.TaskManagementSystem.DTO.Register;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createToken(JwtRequest jwtRequest);

    boolean register(Register register);
}
