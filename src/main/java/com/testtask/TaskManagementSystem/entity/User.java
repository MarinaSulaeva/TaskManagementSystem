package com.testtask.TaskManagementSystem.entity;

import com.testtask.TaskManagementSystem.DTO.Role;
import lombok.*;

import javax.persistence.*;

/**
 * Класс-сущность пользователя, сохраняемая в базе данных
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
