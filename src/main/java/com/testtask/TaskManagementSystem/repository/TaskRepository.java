package com.testtask.TaskManagementSystem.repository;

import com.testtask.TaskManagementSystem.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query(value = "SELECT * FROM task " +
            "WHERE users_id = :userId", nativeQuery = true)
    List<Task> findAllByAuthor(Integer userId);

    @Query(value = "SELECT * FROM task " +
            "WHERE executor_id = :userId", nativeQuery = true)
    List<Task> findAllByExecutor(Integer userId);
}
