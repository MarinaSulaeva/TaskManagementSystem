package com.testtask.TaskManagementSystem.service;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    void createComment(String username, Integer idTask, String text);
    CommentDTO changeComment(String username, Integer idComment, String newText);

    void deleteComment(String username, Integer idComment);

    CommentDTO getComment(Integer idComment);

    List<CommentDTO> getAllCommentsForTask(Integer idTask, Integer page);

    List<CommentDTO> getAllCommentsForAuthor(String username, Integer page);



}
