package com.testtask.TaskManagementSystem.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Users author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;
    private LocalDateTime createdAt;
    private String text;

    public Comment(Users author, Task task, LocalDateTime createdAt, String text) {
        this.author = author;
        this.task = task;
        this.createdAt = createdAt;
        this.text = text;
    }
}
