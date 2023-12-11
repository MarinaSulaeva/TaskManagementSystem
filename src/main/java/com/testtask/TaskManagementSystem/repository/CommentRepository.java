package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value ="SELECT * FROM comment WHERE task_id = :idTask", nativeQuery = true)
    Page<Comment> findAllByTask(Integer idTask, PageRequest pageRequest);
    @Query(value ="SELECT * FROM comment WHERE users_id = :userId", nativeQuery = true)
    Page<Comment> findAllByAuthor(Integer userId, PageRequest pageRequest);


}
