package com.testtask.TaskManagementSystem.entity;

import com.testtask.TaskManagementSystem.DTO.Priority;
import com.testtask.TaskManagementSystem.DTO.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
    private Users author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private Users executor;

    public Task(String title, String description, Status status, Priority priority, Users author, Users executor) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executor = executor;
    }
}
