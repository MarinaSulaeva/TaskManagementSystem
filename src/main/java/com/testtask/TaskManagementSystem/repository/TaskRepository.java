package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Task;
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

    @Query(value = "SELECT * FROM task " +
            "WHERE author_id = :userId", nativeQuery = true)
    Page<Task> findAllByAuthor(Integer userId, PageRequest pageable);

    @Query(value = "SELECT * FROM task " +
            "WHERE executor_id = :userId", nativeQuery = true)
    Page<Task> findAllByExecutor(Integer userId, PageRequest pageable);

}
