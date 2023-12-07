package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    void createComment(String username, Integer idTask);
    CommentDTO changeComment(String username, Integer idTask, Integer idComment);

    void deleteComment(String username, Integer idTask, Integer idComment);

    CommentDTO getComment(String username, Integer idTask, Integer idComment);

    List<CommentDTO> getAllCommentsForTask(String username, Integer idTask);

}
