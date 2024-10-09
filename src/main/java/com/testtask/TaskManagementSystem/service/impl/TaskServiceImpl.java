package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.*;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.User;
import com.testtask.TaskManagementSystem.exceptions.*;
import com.testtask.TaskManagementSystem.mapper.TaskCreateMapper;
import com.testtask.TaskManagementSystem.mapper.TaskMapper;
import com.testtask.TaskManagementSystem.mapper.UsersMapper;
import com.testtask.TaskManagementSystem.repository.TaskRepository;
import com.testtask.TaskManagementSystem.repository.UsersRepository;
import com.testtask.TaskManagementSystem.service.TaskService;
import com.testtask.TaskManagementSystem.service.UserService;
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
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final TaskCreateMapper taskCreateMapper;
    private final UsersMapper usersMapper;


    /**
     * Метод для проверки прав для изменения задачи
     */
    private Task checkTasksAuthor(String username, Integer id, boolean accessForExecutor) {
        Task task = getTask(id);
        User user = userService.findUserByUserName(username);
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
//        Task task = taskForCreate.toTask();
        Task task = taskCreateMapper.toModel(taskForCreate);
        task.setStatus(Status.CREATED);
        task.setExecutor(usersRepository.findByUsername(taskForCreate.getExecutorUsername()).orElse(null));
        task.setAuthor(userService.findUserByUserName(username));
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
//        return TaskDTO.fromTask(taskRepository.save(task));
        return taskMapper.toDTO(taskRepository.save(task));
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
//        return TaskDTO.fromTask(getTask(idTask));
        return taskMapper.toDTO(getTask(idTask));
    }

    /**0
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
        User user = userService.findUserByUserName(usernameExecutor);
        task.setExecutor(user);
        Task resultTask = taskRepository.save(task);
//        return UsersDTO.fromUser(resultTask.getExecutor());
        return usersMapper.toDTO(resultTask.getExecutor());
    }

    /**
     * Метод получения всех созданных задач другим пользователем
     */
    @Override
    public List<TaskDTO> getAllTaskToOtherAuthors(String usernameAuthor, Integer page) {
        User user = userService.findUserByUserName(usernameAuthor);
        List<Task> taskList = taskRepository.findByAuthor(user, PageRequest.of(page, 10)).stream().collect(Collectors.toList());
        if (!taskList.isEmpty()) {
//            return taskList.stream()
//                    .map(TaskDTO::fromTask)
//                    .collect(Collectors.toList());
            return taskMapper.toTaskDTOList(taskList);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Метод получения всех задач для выполнения
     */
    @Override
    public List<TaskDTO> getAllTaskForExecutor(String username, Integer page) {
        User user = userService.findUserByUserName(username);
        List<Task> taskList = taskRepository.findByExecutor(user, PageRequest.of(page, 10)).stream().collect(Collectors.toList());
        if (taskList.isEmpty()) {
            return new ArrayList<>();
        } else {
//            return taskList.stream()
//                    .map(TaskDTO::fromTask)
//                    .collect(Collectors.toList());
            return taskMapper.toTaskDTOList(taskList);
        }
    }

    /**
     * Метод получения всех задач
     */
    @Override
    public List<TaskDTO> getAllTask(Integer page) {
        List<Task> taskList = taskRepository.findAll(PageRequest.of(page, 10)).stream().collect(Collectors.toList());
        if (taskList.isEmpty()) {
            return new ArrayList<>();
        } else {
//            return taskList.stream().map(TaskDTO::fromTask).collect(Collectors.toList());
            return taskMapper.toTaskDTOList(taskList);
        }
    }

    @Override
    public Task getTask(Integer id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("задача не найдена"));
    }
}
