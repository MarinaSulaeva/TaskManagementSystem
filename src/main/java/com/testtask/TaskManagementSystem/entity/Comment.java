package com.testtask.TaskManagementSystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Users author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;
    private LocalDateTime createdAt;
    private String text;

}
