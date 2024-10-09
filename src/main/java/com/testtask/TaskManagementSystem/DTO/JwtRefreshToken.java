package com.testtask.TaskManagementSystem.DTO;

import lombok.Data;
/**
 * Класс для получения токена по рефреш-токену
 */
@Data
public class JwtRefreshToken {
    private String refreshToken;
}
