package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.service.AuthService;
import com.testtask.TaskManagementSystem.swagger.AuthControllerInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Класс-контроллер для регистрации и получения токена
 */
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {
    private final AuthService authService;

    /**
     * Метод для регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid Register register) {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Метод для получения токенов
     */
    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createToken(@RequestBody @Valid JwtRequest request) {
        return ResponseEntity.ok(authService.createToken(request));
    }

    /**
     * Метод для обновления токена доступа по рефреш-токену
     */
    @PostMapping("/auth/refresh_token")
    public ResponseEntity<JwtRefreshResponse> getRefreshToken(@RequestBody JwtRefreshToken jwtRefreshToken) {
        return ResponseEntity.ok(authService.refreshToken(jwtRefreshToken));
    }
}
