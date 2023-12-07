package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    @Query(value ="SELECT * FROM comment WHERE task_id =: idTask", nativeQuery = true)
    List<Comment> findAllByTask(Integer idTask);
    @Query(value ="SELECT * FROM comment WHERE users_id =: userId", nativeQuery = true)
    List<Comment> findAllByAuthor(Integer userId);
}
