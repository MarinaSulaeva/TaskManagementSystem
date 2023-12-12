package com.testtask.TaskManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для получения токена
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;

}
