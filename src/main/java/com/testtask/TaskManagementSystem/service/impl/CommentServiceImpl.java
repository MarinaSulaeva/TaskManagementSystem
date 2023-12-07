package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.CommentDTO;
import com.testtask.TaskManagementSystem.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    @Override
    public void createComment(String username, Integer idTask) {

    }

    @Override
    public CommentDTO changeComment(String username, Integer idTask, Integer idComment) {
        return null;
    }

    @Override
    public void deleteComment(String username, Integer idTask, Integer idComment) {

    }

    @Override
    public CommentDTO getComment(String username, Integer idTask, Integer idComment) {
        return null;
    }

    @Override
    public List<CommentDTO> getAllCommentsForTask(String username, Integer idTask) {
        return null;
    }
}
