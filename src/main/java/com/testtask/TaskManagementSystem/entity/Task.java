package com.testtask.TaskManagementSystem.entity;

import com.testtask.TaskManagementSystem.DTO.Priority;
import com.testtask.TaskManagementSystem.DTO.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * Класс-сущность задачи, сохраняемая в базе данных
 */
@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private User executor;

    public Task(String title, String description, Status status, Priority priority, User author, User executor) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executor = executor;
    }
}
