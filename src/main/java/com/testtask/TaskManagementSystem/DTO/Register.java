package com.testtask.TaskManagementSystem.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Register {
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
    private String username;
    @Size(message = "введите от 8 до 16 символов", min = 8, max = 16)
    private String password;
    private Role role;
}
