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

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository usersRepository;

    private void checkTasksAuthor(String username, Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("задача не найдена"));
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("пользователь с таким логином не найден"));
        if (!user.equals(task.getAuthor())) {
            throw new TaskDoesNotBelongToUserException("данная задача принадлежит другому пользователю");
        }


    }

    @Override
    public void createTask(String username, TaskDTO taskDTO) {
        //добавить проверку, что задача принадлежит пользователю
        Task task = taskDTO.toTask();
        task.setExecutors(new ArrayList<Users>());
        task.setCommentList(new ArrayList<Comment>());
        taskRepository.save(task);
    }

    @Override
    public TaskDTO editTask(String username, TaskDTO taskDTO) {
        //добавить проверку что id существует
        //добавить проверку, что задача принадлежит пользователю
        return TaskDTO.fromTask(taskRepository.save(taskDTO.toTask()));
    }

    @Override
    public void deleteTask(String username, Integer idTask) {
        //добавить проверку, что id существует
        //добавить проверку, что задача принадлежит пользователю
        taskRepository.deleteById(idTask);
    }

    @Override
    public List<TaskDTO> getAllTask(String username) {
        return null;
    }

    @Override
    public TaskDTO getTaskById(String username, Integer idTask) {
        return null;
    }

    @Override
    public Status changeStatusOfTask(String username, Integer idTask, Status newStatus) {
        return null;
    }

    @Override
    public List<UsersDTO> addExecutorsForTask(String username, Integer idTask, List<UsersDTO> usersDTOList) {
        return null;
    }

    @Override
    public List<TaskDTO> getAllTaskToOtherAuthors(String username, String usernameForOtherUser) {
        return null;
    }
}
