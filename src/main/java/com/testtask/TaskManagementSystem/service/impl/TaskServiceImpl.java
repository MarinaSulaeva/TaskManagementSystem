package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.Users;
import com.testtask.TaskManagementSystem.exceptions.TaskDoesNotBelongToUserException;
import com.testtask.TaskManagementSystem.exceptions.TaskNotFoundException;
import com.testtask.TaskManagementSystem.exceptions.UserNotFoundException;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    private Task checkTasksAuthor(String username, Integer id, boolean accessForExecutor) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("задача не найдена"));
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден"));
        if (accessForExecutor) {
            if (!user.equals(task.getAuthor()) || !user.equals(task.getExecutor())) {
                throw new TaskDoesNotBelongToUserException("данная задача принадлежит другому пользователю");
            }
        }
       else if (!user.equals(task.getAuthor())) {
            throw new TaskDoesNotBelongToUserException("данная задача принадлежит другому пользователю");
        }

        return task;
    }

    @Override
    public void createTask(String username, TaskForCreate taskForCreate) {
        Task task = new Task();
        task.setTitle(taskForCreate.getTitle());
        task.setPriority(taskForCreate.getPriority());
        task.setDescription(taskForCreate.getDescription());
        task.setStatus(Status.CREATED);
        task.setExecutor(usersRepository.findByUsername(taskForCreate.getExecutorUsername()).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        task.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        taskRepository.save(task);
    }

    @Override
    public TaskDTO editTask(String username, TaskForChange taskForChange) {
        Task task = checkTasksAuthor(username, taskForChange.getId(), false);
        task.setDescription(taskForChange.getDescription());
        task.setPriority(taskForChange.getPriority());
        task.setTitle(taskForChange.getTitle());

        return TaskDTO.fromTask(taskRepository.save(task));
    }

    @Override
    public void deleteTask(String username, Integer idTask) {
        taskRepository.delete(checkTasksAuthor(username, idTask, false));
    }

    @Override
    public List<TaskDTO> getAllTask(String username) {
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден"));
        return taskRepository.findAllByAuthor(users.getId())
                .stream()
                .map(TaskDTO :: fromTask)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(Integer idTask) {
        return TaskDTO.fromTask(taskRepository
                .findById(idTask)
                .orElseThrow(() -> new TaskNotFoundException("задача не найдена")));
    }

    @Override
    public void changeStatusOfTask(String username, Integer idTask, Status newStatus) {
        Task task = checkTasksAuthor(username, idTask, true);
        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    @Override
    public UsersDTO addExecutorForTask(String username, Integer idTask, String usernameExecutor) {
        Task task = checkTasksAuthor(username, idTask, false);
        Users user = usersRepository.findByUsername(usernameExecutor).orElseThrow(() -> new UserNotFoundException("пользователь не найден"));
        task.setExecutor(user);
        return UsersDTO.fromUser(taskRepository.save(task).getExecutor());
    }

    @Override
    public List<TaskDTO> getAllTaskToOtherAuthors(String usernameAuthor) {
        return taskRepository.findAllByAuthor(usersRepository.findByUsername(usernameAuthor).orElseThrow(() -> new UserNotFoundException("пользователь не найден")).getId())
                    .stream()
                    .map(TaskDTO :: fromTask)
                    .collect(Collectors.toList());

    }

    @Override
    public List<TaskDTO> getAllTaskForExecutor(String username) {
        return taskRepository.findAllByExecutor(usersRepository
                        .findByUsername(username)
                        .orElseThrow(() ->
                                new UserNotFoundException("пользователь с таким логином не найден")).getId())
                .stream()
                .map(TaskDTO :: fromTask)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllTask() {
        return taskRepository.findAll().stream().map(TaskDTO ::fromTask).collect(Collectors.toList());
    }


}
