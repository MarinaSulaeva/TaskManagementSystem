package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.service.CommentService;
import com.testtask.TaskManagementSystem.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final TaskService taskService;
    private final CommentService commentService;

    @GetMapping("/myComments/{page}")
    @SecurityRequirement(name = "JWT")
    public List<CommentDTO> getAllMyComments(Authentication authentication, @PathVariable("page") Integer page) {
        return commentService.getAllCommentsForAuthor(authentication.getName(), page);
    }

    @GetMapping("/my_task_for_execution/page")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getMyTasksForExecution(Authentication authentication, @RequestParam("page") Integer page) {
        return taskService.getAllTaskForExecutor(authentication.getName(), page);
    }
}
