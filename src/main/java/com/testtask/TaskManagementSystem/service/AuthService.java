package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.JwtRequest;
import com.testtask.TaskManagementSystem.DTO.JwtResponse;
import com.testtask.TaskManagementSystem.DTO.Register;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<JwtResponse> createToken(JwtRequest jwtRequest);

    public boolean register(Register register);
}
