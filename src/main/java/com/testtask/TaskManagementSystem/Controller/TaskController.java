package com.testtask.TaskManagementSystem.Controller;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @SecurityRequirement(name = "JWT")
    public void createTask(Authentication authentication, @RequestBody @Valid TaskForCreate taskForCreate) {
        taskService.createTask(authentication.getName(), taskForCreate);
    }

    @PutMapping
    @SecurityRequirement(name = "JWT")
    public TaskDTO editTask(Authentication authentication, @RequestBody @Valid TaskForChange taskForChange) {
        return taskService.editTask(authentication.getName(), taskForChange);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public void deleteTask(Authentication authentication, @PathVariable Integer id) {
        taskService.deleteTask(authentication.getName(), id);
    }

    @GetMapping("/myTask/{page}")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getAllMyTasks(Authentication authentication, @PathVariable("page") Integer page) {
        return taskService.getAllMyTasks(authentication.getName(), page);
    }


    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public TaskDTO getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PatchMapping("/{id}/status")
    @SecurityRequirement(name = "JWT")
    public void changeStatusOfTask(Authentication authentication, @PathVariable Integer id, Status newStatus) {
        taskService.changeStatusOfTask(authentication.getName(), id, newStatus);
    }

    @PatchMapping("/{id}/executor")
    @SecurityRequirement(name = "JWT")
    public UsersDTO addExecutorForTask(Authentication authentication, @PathVariable Integer id, @Valid UsersDTO usersDTO) {
        return taskService.addExecutorForTask(authentication.getName(), id, usersDTO.getEmail());
    }

    @GetMapping("/username/{page}")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getAllTaskForOtherUser(@RequestParam("username") @Valid UsersDTO usersDTO, @PathVariable("page") Integer page) {
        return taskService.getAllTaskToOtherAuthors(usersDTO.getEmail(), page);
    }

    @GetMapping("/all/{page}")
    @SecurityRequirement(name = "JWT")
    public List<TaskDTO> getAllTask(@PathVariable("page") Integer page) {
        return taskService.getAllTask(page);
    }
}
