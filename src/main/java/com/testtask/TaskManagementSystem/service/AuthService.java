package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.*;
import org.springframework.http.ResponseEntity;

/**
 * Интерфейс для регистрации пользователей и получения токена
 */
public interface AuthService {

    JwtResponse createToken(JwtRequest jwtRequest);

    boolean register(Register register);

    JwtRefreshResponse refreshToken(JwtRefreshToken jwtRefreshToken);

}
