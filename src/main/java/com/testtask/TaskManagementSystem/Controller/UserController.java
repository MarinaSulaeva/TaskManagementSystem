package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.service.CommentService;
import com.testtask.TaskManagementSystem.service.TaskService;
import com.testtask.TaskManagementSystem.swagger.UserControllerInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс-контроллер для пользователя
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerInterface {
    private final TaskService taskService;
    private final CommentService commentService;

    /**
     * Метод для получения комментарий пользователя
     */
    @GetMapping("/my_сomments/page")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<CommentDTO>> getAllMyComments(Authentication authentication,
                                             @RequestParam("page") Integer page) {
        return ResponseEntity.ok(commentService.getAllCommentsForAuthor(authentication.getName(), page));
    }

    /**
     * Метод для получения задач для исполнения
     */
    @GetMapping("/my_task_for_execution/page")
    public ResponseEntity<List<TaskDTO>> getMyTasksForExecution(Authentication authentication,
                                                @RequestParam("page") Integer page) {
        return ResponseEntity.ok(taskService.getAllTaskForExecutor(authentication.getName(), page));
    }

    /**
     * Метод для получения задач пользователя
     */
    @GetMapping("/my_task/page")
    public ResponseEntity<List<TaskDTO>> getAllMyTasks(Authentication authentication,
                                                      @RequestParam("page") Integer page) {
        return ResponseEntity.ok(taskService.getAllMyTasks(authentication.getName(), page));
    }

}
