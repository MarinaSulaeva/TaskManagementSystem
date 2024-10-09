package com.testtask.TaskManagementSystem.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс получения задачи
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UsersDTO author;
    private UsersDTO executor;
}
