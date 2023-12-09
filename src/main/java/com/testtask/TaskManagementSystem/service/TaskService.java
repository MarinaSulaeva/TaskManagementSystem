package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.*;

import java.util.List;

public interface TaskService {
    void createTask(String username, TaskForCreate taskForCreate);
    TaskDTO editTask(String username, TaskForChange taskForChange);
    void deleteTask(String username, Integer idTask);
    List<TaskDTO> getAllTask(String username);
    TaskDTO getTaskById(Integer idTask);
    void changeStatusOfTask(String username, Integer idTask, Status newStatus);
    UsersDTO addExecutorForTask(String username, Integer idTask, String usernameExecutor);
    List<TaskDTO> getAllTaskToOtherAuthors(String usernameAuthor);
    List<TaskDTO> getAllTaskForExecutor(String username);

    List<TaskDTO> getAllTask();



}
