package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.service.CommentService;
import com.testtask.TaskManagementSystem.service.TaskService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final TaskService taskService;
    private final CommentService commentService;

    @GetMapping("/myComments")
    @SecurityRequirement(name = "JWT")
    public List<CommentDTO> getAllMyComments(Authentication authentication) {
        return commentService.getAllCommentsForAuthor(authentication.getName());
    }

    @GetMapping("/my_task_for_execution")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getMyTasksForExecution(Authentication authentication) {
        return taskService.getAllTaskForExecutor(authentication.getName());
    }
}
