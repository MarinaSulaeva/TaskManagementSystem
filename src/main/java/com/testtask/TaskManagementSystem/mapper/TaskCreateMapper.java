package com.testtask.TaskManagementSystem.mapper;

import com.testtask.TaskManagementSystem.DTO.TaskForCreate;
import com.testtask.TaskManagementSystem.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TaskCreateMapper {
    Task toModel(TaskForCreate taskForCreate);
}
