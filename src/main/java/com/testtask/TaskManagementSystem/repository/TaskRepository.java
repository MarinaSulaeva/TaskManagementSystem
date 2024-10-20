package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Task;
import com.testtask.TaskManagementSystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с сущностью задачи в базе данных
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findByAuthor(User user, PageRequest pageable);


    Page<Task> findByExecutor(User user, PageRequest pageable);

}
