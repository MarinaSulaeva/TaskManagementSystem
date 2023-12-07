package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
