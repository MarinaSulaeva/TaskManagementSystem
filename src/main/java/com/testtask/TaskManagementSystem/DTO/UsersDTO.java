package com.testtask.TaskManagementSystem.DTO;

import com.testtask.TaskManagementSystem.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    @Size(message = "введите от 4 до 32 символов", min = 4, max = 32)
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
