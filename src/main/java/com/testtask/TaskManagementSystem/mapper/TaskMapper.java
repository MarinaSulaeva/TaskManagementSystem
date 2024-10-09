package com.testtask.TaskManagementSystem.mapper;

import com.testtask.TaskManagementSystem.DTO.TaskDTO;
import com.testtask.TaskManagementSystem.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = UsersMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskMapper {
    TaskDTO toDTO(Task task);

    List<TaskDTO> toTaskDTOList(List<Task> tasks);
}
