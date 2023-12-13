package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.exceptions.*;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-сервис для работы с задачами
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    /**
     * Метод для проверки прав для изменения задачи
     */
    private Task checkTasksAuthor(String username, Integer id, boolean accessForExecutor) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("задача не найдена"));
        User user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден"));
        if (!accessForExecutor) {
            if (!user.equals(task.getAuthor())) {
                throw new TaskDoesNotBelongToUserException("данная задача принадлежит другому пользователю");
            }
        } else {
            if (!user.equals(task.getAuthor()) && !user.equals(task.getExecutor())) {
                throw new TaskDoesNotBelongToUserException("данная задача принадлежит другому пользователю");
            }
        }
        return task;
    }

    /**
     * Метод для создания задачи
     */
    @Override
    public Integer createTask(String username, TaskForCreate taskForCreate) {
        Task task = taskForCreate.toTask();
        task.setStatus(Status.CREATED);
        task.setExecutor(usersRepository.findByUsername(taskForCreate.getExecutorUsername()).orElse(null));
        task.setAuthor(usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден")));
        return taskRepository.save(task).getId();
    }

    /**
     * Метод для изменения задачи
     */
    @Override
    public TaskDTO editTask(String username, TaskForChange taskForChange) {
        Task task = checkTasksAuthor(username, taskForChange.getId(), false);
        task.setDescription(taskForChange.getDescription());
        task.setPriority(taskForChange.getPriority());
        task.setTitle(taskForChange.getTitle());
        return TaskDTO.fromTask(taskRepository.save(task));
    }

    /**
     * Метод для удаления задачи
     */
    @Override
    public void deleteTask(String username, Integer idTask) {
        taskRepository.delete(checkTasksAuthor(username, idTask, false));
    }

    /**
     * Метод получения всех созданных задач пользователя
     */
    @Override
    public List<TaskDTO> getAllMyTasks(String username, Integer page) {
        return getAllTaskToOtherAuthors(username, page);
    }

    /**
     * Метод получения задачи по id
     */
    @Override
    public TaskDTO getTaskById(Integer idTask) {
        return TaskDTO.fromTask(taskRepository
                .findById(idTask)
                .orElseThrow(() -> new TaskNotFoundException("задача не найдена")));
    }

    /**
     * Метод для изменения статуса задачи
     */
    @Override
    public void changeStatusOfTask(String username, Integer idTask, Status newStatus) {
        Task task = checkTasksAuthor(username, idTask, true);
        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    /**
     * Метод для добавления исполнителя задачи
     */
    @Override
    public UsersDTO addExecutorForTask(String username, Integer idTask, String usernameExecutor) {
        Task task = checkTasksAuthor(username, idTask, false);
        User user = usersRepository.findByUsername(usernameExecutor).orElseThrow(() ->
                new UserNotFoundException("пользователь не найден"));
        task.setExecutor(user);
        Task resultTask = taskRepository.save(task);
        return UsersDTO.fromUser(resultTask.getExecutor());
    }

    /**
     * Метод получения всех созданных задач другим пользователем
     */
    @Override
    public List<TaskDTO> getAllTaskToOtherAuthors(String usernameAuthor, Integer page) {
        User user = usersRepository.findByUsername(usernameAuthor).orElseThrow(() ->
                new UserNotFoundException("пользователь с таким логином не найден"));
        List<Task> taskList = taskRepository.findAllByAuthor(user.getId(), PageRequest.of(page, 10)).stream().toList();
        if (!taskList.isEmpty()) {
            return taskList.stream()
                    .map(TaskDTO::fromTask)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Метод получения всех задач для выполнения
     */
    @Override
    public List<TaskDTO> getAllTaskForExecutor(String username, Integer page) {
        User user = usersRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("пользователь с таким логином не найден"));
        List<Task> taskList = taskRepository.findAllByExecutor(user.getId(), PageRequest.of(page, 10)).stream().toList();
        if (taskList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return taskList.stream()
                    .map(TaskDTO::fromTask)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Метод получения всех задач
     */
    @Override
    public List<TaskDTO> getAllTask(Integer page) {
        List<Task> taskList = taskRepository.findAll(PageRequest.of(page, 10)).stream().toList();
        if (taskList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return taskList.stream().map(TaskDTO::fromTask).collect(Collectors.toList());
        }
    }
}
