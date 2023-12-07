package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Integer id;
    private String email;
    private String password;

    public Users toUser() {
        return new Users(this.id, this.email, this.password);
    }

    public static UsersDTO fromUser(Users users) {
        return new UsersDTO(users.getId(), users.getEmail(), users.getPassword());
    }
}
