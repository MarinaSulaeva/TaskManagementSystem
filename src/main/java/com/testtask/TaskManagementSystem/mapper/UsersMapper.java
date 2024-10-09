package com.testtask.TaskManagementSystem.mapper;

import com.testtask.TaskManagementSystem.DTO.UsersDTO;
import com.testtask.TaskManagementSystem.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {
    @Mapping(target = "email", source = "username")
    UsersDTO toDTO(User user);

}
