package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Comment;
import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с сущностью комментария в базе данных
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findByTask(Task task, PageRequest pageRequest);

    Page<Comment> findByAuthor(User user, PageRequest pageRequest);


}
