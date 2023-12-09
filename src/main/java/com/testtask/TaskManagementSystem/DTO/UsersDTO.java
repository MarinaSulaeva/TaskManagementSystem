package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private String email;


    public Users toUser() {
        Users users = new Users();
        users.setUsername(this.email);
        return users;
    }

    public static UsersDTO fromUser(Users users) {
        return new UsersDTO(users.getUsername());
    }
}
