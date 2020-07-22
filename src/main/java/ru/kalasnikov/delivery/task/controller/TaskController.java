package ru.kalasnikov.delivery.task.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.kalasnikov.delivery.task.dto.TaskDto;
import ru.kalasnikov.delivery.task.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Slf4j
@AllArgsConstructor
public class TaskController {
    private final TaskService service;

    @PostMapping
    public TaskDto create(@RequestBody TaskDto taskDto) {
        if (taskDto == null) {
            throw new RuntimeException("Невозможно создать Task с пустыми полями");
        }

        return service.create(taskDto);
    }

    @GetMapping
    public List<TaskDto> getResult(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                   @RequestParam(required = false) Long orderNumber) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new RuntimeException("Дата / время начала диапазона фильтрации не может быть после даты / времени завершения");
        }
        return service.getResultRequestFromTaskTable(start, end, orderNumber);
    }

}
