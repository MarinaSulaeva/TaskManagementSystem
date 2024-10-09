package com.testtask.TaskManagementSystem.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Класс для прохождения аутентификации по токену
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
    private String username;
    @Size(message = "введите от 8 до 16 символов", min = 8, max = 16)
    private String password;

}
