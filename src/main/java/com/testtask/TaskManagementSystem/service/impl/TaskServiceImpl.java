package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.Status;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.DTO.UsersDTO;
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
    public void createTask(String username, TaskDTO taskDTO) {
        Task task = taskDTO.toTask();
        task.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        taskRepository.save(task);
    }

    @Override
    public TaskDTO editTask(String username, TaskDTO taskDTO) {
        checkTasksAuthor(username, taskDTO.getId(), false);
        return TaskDTO.fromTask(taskRepository.save(taskDTO.toTask()));
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
    public UsersDTO addExecutorForTask(String username, Integer idTask, UsersDTO executor) {
        Task task = checkTasksAuthor(username, idTask, false);
        task.setExecutor(executor.toUser());
        return UsersDTO.fromUser(taskRepository.save(task).getExecutor());
    }

    @Override
    public List<TaskDTO> getAllTaskToOtherAuthors(String username, String usernameForOtherUser) {
        if (username.equals(usernameForOtherUser)) {
            return getAllTask(username);
        } else {

            return taskRepository.findAllByAuthor(usersRepository
                    .findByUsername(username)
                    .orElseThrow(() ->
                            new UserNotFoundException("пользователь с таким логином не найден")).getId())
                    .stream()
                    .map(TaskDTO :: fromTask)
                    .collect(Collectors.toList());
        }
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


}
