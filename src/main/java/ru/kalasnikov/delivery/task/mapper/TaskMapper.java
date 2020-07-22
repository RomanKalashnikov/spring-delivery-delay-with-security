package ru.kalasnikov.delivery.task.mapper;

import ru.kalasnikov.delivery.task.dto.TaskDto;
import ru.kalasnikov.delivery.task.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toDomain(TaskDto dto);

    TaskDto toDTO(Task domain);

    List<Task> toDomainList(List<TaskDto> customDtoList);

    List<TaskDto> toDTOList(List<Task> list);

}
