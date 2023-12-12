package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.*;

import java.util.List;

/**
 * Интерфейс для работы с задачами
 */
public interface TaskService {
    Integer createTask(String username, TaskForCreate taskForCreate);

    TaskDTO editTask(String username, TaskForChange taskForChange);

    void deleteTask(String username, Integer idTask);

    List<TaskDTO> getAllMyTasks(String username, Integer page);

    TaskDTO getTaskById(Integer idTask);

    void changeStatusOfTask(String username, Integer idTask, Status newStatus);

    UsersDTO addExecutorForTask(String username, Integer idTask, String usernameExecutor);

    List<TaskDTO> getAllTaskToOtherAuthors(String usernameAuthor, Integer page);

    List<TaskDTO> getAllTaskForExecutor(String username, Integer page);

    List<TaskDTO> getAllTask(Integer page);


}
