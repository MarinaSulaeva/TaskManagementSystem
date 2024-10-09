package com.testtask.TaskManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * Класс-обертка для получения комметариев
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private UsersDTO usersDTO;
    private TaskDTO task;
    private LocalDateTime createdAt;
    private String text;
}
