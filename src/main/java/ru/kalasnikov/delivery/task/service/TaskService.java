package ru.kalasnikov.delivery.task.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kalasnikov.delivery.task.dto.TaskDto;
import ru.kalasnikov.delivery.task.entity.Task;
import ru.kalasnikov.delivery.task.mapper.TaskMapper;
import ru.kalasnikov.delivery.task.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class TaskService {

    private final TaskMapper mapper;
    private final TaskRepository repository;


    public TaskDto create(TaskDto taskDto) {
        Task save = repository.saveNewTaskIntoTable(mapper.toDomain(taskDto));
        return mapper.toDTO(save);
    }


    public List<TaskDto> getResultRequestFromTaskTable(LocalDateTime start, LocalDateTime end, Long orderNumber) {
        List<Task> taskList = repository.findTasksByCreateTimeIsBetweenAndOrderNumber(start, end, orderNumber);

        return taskList.stream().map(mapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

}
