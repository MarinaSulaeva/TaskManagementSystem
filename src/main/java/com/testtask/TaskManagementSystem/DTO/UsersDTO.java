package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Integer id;
    private String username;
    private String password;
    private Role role;

    public Users toUser() {
        return new Users(this.id, this.username, this.password, this.role);
    }

    public static UsersDTO fromUser(Users users) {
        return new UsersDTO(users.getId(), users.getUsername(), users.getPassword(), users.getRole());
    }
}
