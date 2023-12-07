package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.Status;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.DTO.UsersDTO;
import com.testtask.TaskManagementSystem.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public void createTask(Authentication authentication, @RequestBody @Valid TaskDTO taskDTO) {
        taskService.createTask(authentication.getName(), taskDTO);
    }

    @PutMapping
    public TaskDTO editTask(Authentication authentication, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.editTask(authentication.getName(), taskDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(Authentication authentication, @PathVariable Integer id) {
        taskService.deleteTask(authentication.getName(), id);
    }

    @GetMapping
    public List<TaskDTO> getAllTask(Authentication authentication) {
        return taskService.getAllTask(authentication.getName());
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PatchMapping("/{id}/status")
    public void changeStatusOfTask(Authentication authentication, @PathVariable Integer id, Status newStatus) {
        taskService.changeStatusOfTask(authentication.getName(), id, newStatus);
    }

    @PatchMapping("/{id}/executors")
    public UsersDTO addExecutorsForTask(Authentication authentication, @PathVariable Integer id, UsersDTO executor) {
        return taskService.addExecutorForTask(authentication.getName(), id, executor);
    }

    @GetMapping("/{userId}")
    public List<TaskDTO> getAllTaskForOtherUser(@PathVariable("userId") Integer id) {
        return getAllTaskForOtherUser(id);
    }

//    добавить объекты для валидации
}
