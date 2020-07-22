package ru.kalasnikov.delivery.task.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kalasnikov.delivery.task.dto.TaskDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {
    private final TaskService service;

    @Autowired
    TaskServiceTest(TaskService service) {
        this.service = service;
    }

    @Test
    void whenParametersOnlyOrderNumberGetResultExpectEmptyList() {
        List<TaskDto> result = service.getResultRequestFromTaskTable( null, null,7777L) ;
        assertTrue(result.isEmpty());
    }
}