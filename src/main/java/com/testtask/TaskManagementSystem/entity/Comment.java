package com.testtask.TaskManagementSystem.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.time.LocalDateTime;
/**
 * Класс-сущность комментария, сохраняемая в базе данных
 */
@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;
    private LocalDateTime createdAt;
    private String text;

    public Comment(User author, Task task, LocalDateTime createdAt, String text) {
        this.author = author;
        this.task = task;
        this.createdAt = createdAt;
        this.text = text;
    }
}
