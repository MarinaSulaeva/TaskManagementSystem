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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    private Task checkTasksAuthor(String username, Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("задача не найдена"));
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден"));
        if (!user.equals(task.getAuthor())) {
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
        checkTasksAuthor(username, taskDTO.getId());
        return TaskDTO.fromTask(taskRepository.save(taskDTO.toTask()));
    }

    @Override
    public void deleteTask(String username, Integer idTask) {
        taskRepository.delete(checkTasksAuthor(username, idTask));
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
        Task task = checkTasksAuthor(username, idTask);
        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    @Override
    public List<UsersDTO> addExecutorsForTask(String username, Integer idTask, List<UsersDTO> usersDTOList) {
        Task task = checkTasksAuthor(username, idTask);
        List<Users> newExecutors = usersDTOList.stream().map(UsersDTO :: toUser).collect(Collectors.toList());
        List<Users> executors = task.getExecutors();
        if (executors.isEmpty()) {
            task.setExecutors(newExecutors);
        } else executors.addAll(newExecutors);
        return executors.stream().map(UsersDTO :: fromUser).collect(Collectors.toList());
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
}
