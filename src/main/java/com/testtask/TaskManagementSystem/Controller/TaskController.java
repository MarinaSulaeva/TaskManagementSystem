package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

/**
 * Класс-контроллер для работы с с задачами
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Метод для создания задачи
     */
    @PostMapping
    @SecurityRequirement(name = "JWT")
    public Integer createTask(Authentication authentication,
                              @RequestBody @Valid TaskForCreate taskForCreate) {
        return taskService.createTask(authentication.getName(), taskForCreate);
    }

    /**
     * Метод для изменения задачи
     */
    @PutMapping
    @SecurityRequirement(name = "JWT")
    public TaskDTO editTask(Authentication authentication,
                            @RequestBody @Valid TaskForChange taskForChange) {
        return taskService.editTask(authentication.getName(), taskForChange);
    }

    /**
     * Метод для удаления задачи
     */
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public void deleteTask(Authentication authentication,
                           @PathVariable Integer id) {
        taskService.deleteTask(authentication.getName(), id);
    }

    /**
     * Метод для получения задачи по id
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public TaskDTO getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    /**
     * Метод для изменения статуса задачи
     */
    @PatchMapping("/{id}/status")
    @SecurityRequirement(name = "JWT")
    public void changeStatusOfTask(Authentication authentication,
                                   @PathVariable Integer id,
                                   @RequestBody ChangeStatus newStatus) {
        taskService.changeStatusOfTask(authentication.getName(), id, newStatus.getStatus());
    }

    /**
     * Метод для добавления/изменения исполнителя задачи
     */
    @PatchMapping("/{id}/executor")
    @SecurityRequirement(name = "JWT")
    public UsersDTO addExecutorForTask(Authentication authentication,
                                       @PathVariable Integer id,
                                       @Valid UsersDTO usersDTO) {
        return taskService.addExecutorForTask(authentication.getName(), id, usersDTO.getEmail());
    }

    /**
     * Метод для получения задач другого пользователя
     */
    @GetMapping("/other/page")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getAllTaskForOtherUser(@RequestBody @Valid UsersDTO usersDTO,
                                                @RequestParam("page") Integer page) {
        return taskService.getAllTaskToOtherAuthors(usersDTO.getEmail(), page);
    }

    /**
     * Метод получения всех задач
     */
    @GetMapping("/all/page")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getAllTask(@RequestParam("page") Integer page) {
        return taskService.getAllTask(page);
    }
}
