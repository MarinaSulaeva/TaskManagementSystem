package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.CreateOrUpdateComment;

import java.util.List;

public interface CommentService {
    void createComment(String username, Integer idTask, CreateOrUpdateComment comment);
    CommentDTO changeComment(String username, Integer idComment, CreateOrUpdateComment comment);

    void deleteComment(String username, Integer idComment);

    CommentDTO getComment(Integer idComment);

    List<CommentDTO> getAllCommentsForTask(Integer idTask, Integer page);

    List<CommentDTO> getAllCommentsForAuthor(String username, Integer page);



}
