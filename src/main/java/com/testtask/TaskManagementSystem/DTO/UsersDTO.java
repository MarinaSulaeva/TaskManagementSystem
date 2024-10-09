package com.testtask.TaskManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Класс для получения пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
    private String email;
}
