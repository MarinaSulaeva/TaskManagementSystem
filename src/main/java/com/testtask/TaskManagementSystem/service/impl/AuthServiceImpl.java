package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.entity.RefreshToken;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.exceptions.JwtAuthenticationException;
import com.testtask.TaskManagementSystem.repository.RefreshTokenRepository;
import com.testtask.TaskManagementSystem.service.AuthService;
import com.testtask.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Класс-сервис для регистрации пользователя и получения токена
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * Метод для получения токена
     */
    public JwtResponse createToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            log.error("Username or password were wrong", e);
            throw new JwtAuthenticationException(e.getMessage());
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String accessToken = jwtTokenUtil.createAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.createRefreshToken(userDetails);
        saveRefreshTokenToDatabase(userService.findUserByUserName(authRequest.getUsername()), refreshToken);
        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken);
        log.info("Access and refresh tokens: {}", jwtResponse);
        return jwtResponse;
    }

    /**
     * Метод для регистрации пользователя
     */
    @Override
    public boolean register(Register register) {
        if (userService.userExists(register.getUsername())) {
            return false;
        }
        String password = encoder.encode(register.getPassword());
        userService.createUser(register, password);
        return true;
    }

    /**
     * Метод для обновления токена с помощью рефреш-токена
     */
    @Override
    public JwtRefreshResponse refreshToken(JwtRefreshToken jwtRefreshToken) {
        String username = jwtTokenUtil.getUsername(jwtRefreshToken.getRefreshToken());
        if (username == null) {
            throw new JwtAuthenticationException("Некорректный токен.");
        }
        UserDetails user = userService.loadUserByUsername(username);
        String jwtAccessToken = jwtTokenUtil.refreshToken(jwtRefreshToken.getRefreshToken(), user);
        return new JwtRefreshResponse(jwtAccessToken);
    }

    /**
     * Метод для сохранения рефреш-токена в базу данных
     */
    private void saveRefreshTokenToDatabase(User user, String token) {
        refreshTokenRepository.save(RefreshToken.
                builder().
                token(token).
                user(user).
                build()

        );
    }
}
