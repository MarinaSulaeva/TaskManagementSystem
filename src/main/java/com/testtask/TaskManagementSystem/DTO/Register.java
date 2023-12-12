package com.testtask.TaskManagementSystem.DTO;


import lombok.Data;
import javax.validation.constraints.Size;

/**
 * Класс для регистрации польщователя
 */
@Data
public class Register {
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
    private String username;
    @Size(message = "введите от 8 до 16 символов", min = 8, max = 16)
    private String password;
    private Role role;
}
