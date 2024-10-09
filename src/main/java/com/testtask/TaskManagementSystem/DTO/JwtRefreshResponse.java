package com.testtask.TaskManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Класс для получения токена по рефреш-токену
 */
@Data
@AllArgsConstructor
public class JwtRefreshResponse {
    private String accessToken;
}
