package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.TaskDTO;

import java.util.List;

public interface UserService {

    List<TaskDTO> getAllTaskAsExecutor(Integer id);
}
