package com.testtask.TaskManagementSystem.entity;

import com.testtask.TaskManagementSystem.DTO.Priority;
import com.testtask.TaskManagementSystem.DTO.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @Enumerated
    private Status status;
    @Enumerated
    private Priority priority;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private Users author;
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Users> executors;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    public Task(Integer id, String title, String description, Status status, Priority priority, Users author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
    }
}
