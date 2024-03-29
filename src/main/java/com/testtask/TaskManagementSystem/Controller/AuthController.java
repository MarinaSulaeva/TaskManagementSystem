package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.JwtRequest;
import com.testtask.TaskManagementSystem.DTO.Register;
import com.testtask.TaskManagementSystem.service.AuthService;
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
public class AuthController {
    private final AuthService authService;

    /**
     * Метод для регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid Register register) throws Exception {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Методя для получения токена
     */
    @PostMapping("/auth")
    public ResponseEntity<?> createToken(@RequestBody @Valid JwtRequest request) {
        return authService.createToken(request);
    }
}
