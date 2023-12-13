package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.DTO.CreateOrUpdateComment;

import java.util.List;

/**
 * Интерфейс для работы с комментариями
 */
public interface CommentService {
    Integer createComment(String username, Integer idTask, CreateOrUpdateComment comment);

    CommentDTO changeComment(String username, Integer idComment, CreateOrUpdateComment comment, Integer idTask);

    void deleteComment(String username, Integer idComment, Integer idTask);

    CommentDTO getComment(Integer idComment, Integer idTask);

    List<CommentDTO> getAllCommentsForTask(Integer idTask, Integer page);

    List<CommentDTO> getAllCommentsForAuthor(String username, Integer page);


}
