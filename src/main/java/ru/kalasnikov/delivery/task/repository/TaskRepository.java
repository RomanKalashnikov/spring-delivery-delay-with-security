package ru.kalasnikov.delivery.task.repository;

import ru.kalasnikov.delivery.task.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository  {
    List<Task> findTasksByCreateTimeIsBetweenAndOrderNumber(LocalDateTime start, LocalDateTime end, Long orderNumber);

    Task saveNewTaskIntoTable(Task task);



}
