package com.testtask.TaskManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

/**
 * Класс изменения задачи
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskForChange {
    @NotNull
    private Integer id;
    @Size(min = 2, max = 32)
    private String title;
    @Size(min = 2, max = 255)
    private String description;
    private Priority priority;

}
