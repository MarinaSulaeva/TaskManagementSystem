package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.Status;
import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.DTO.UsersDTO;

import java.util.List;

public interface TaskService {
    void createTask(String username, TaskDTO taskDTO);
    TaskDTO editTask(String username, TaskDTO taskDTO);
    void deleteTask(String username, Integer idTask);
    List<TaskDTO> getAllTask(String username);
    TaskDTO getTaskById(Integer idTask);
    void changeStatusOfTask(String username, Integer idTask, Status newStatus);
    List<UsersDTO> addExecutorsForTask(String username, Integer idTask, List<UsersDTO> usersDTOList);
    List<TaskDTO> getAllTaskToOtherAuthors(String username, String usernameForOtherUser);



}
