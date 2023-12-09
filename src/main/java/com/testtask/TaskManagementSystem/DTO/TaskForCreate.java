package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Task;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskForCreate {
    @NotNull
    private Integer id;
    @Size(min = 2, max = 32)
    private String title;
    @Size(min = 2, max = 255)
    private String description;
    private Priority priority;
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
    private String executorUsername;


}
