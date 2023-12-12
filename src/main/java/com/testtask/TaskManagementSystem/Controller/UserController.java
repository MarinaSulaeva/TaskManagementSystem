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

/**
 * Класс-контроллер для пользователя
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final TaskService taskService;
    private final CommentService commentService;

    /**
     * Метод для получения комментарий пользователя
     */
    @GetMapping("/my_сomments/page")
    @SecurityRequirement(name = "JWT")
    public List<CommentDTO> getAllMyComments(Authentication authentication,
                                             @RequestParam("page") Integer page) {
        return commentService.getAllCommentsForAuthor(authentication.getName(), page);
    }

    /**
     * Метод для получения задач для исполнения
     */
    @GetMapping("/my_task_for_execution/page")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getMyTasksForExecution(Authentication authentication,
                                                @RequestParam("page") Integer page) {
        return taskService.getAllTaskForExecutor(authentication.getName(), page);
    }

    /**
     * Метод для получения задач пользователя
     */
    @GetMapping("/my_task/page")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getAllMyTasks(Authentication authentication,
                                       @RequestParam("page") Integer page) {
        return taskService.getAllMyTasks(authentication.getName(), page);
    }

}
