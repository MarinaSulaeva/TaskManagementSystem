package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.Users;

import java.time.LocalDateTime;

public class CommentDTO {
    private Integer id;
    private Users author;
    private Task task;
    private LocalDateTime createdAt;
    private String text;
}
