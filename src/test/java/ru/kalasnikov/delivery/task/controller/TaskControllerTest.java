package ru.kalasnikov.delivery.task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskControllerTest {
    private final TaskController controller;

    @Autowired
    TaskControllerTest(TaskController taskController) {
        this.controller = taskController;
    }



    @Test
    void whenCreatingFromNullTaskDtoThrownException_ThenAssertionSucceeds() {
        Exception runtimeException = assertThrows(RuntimeException.class, () -> controller.create(null));
        String expectedMessage = "Невозможно создать Task с пустыми полями";
        String actualMessage = runtimeException.getMessage();


        assertTrue(actualMessage.contains(expectedMessage));


    }

    @Test
    void whenStartDateTimeIsAfterEndDateTimeThrownException_ThenAssertionSucceeds() {
        Exception runtimeException = assertThrows(RuntimeException.class, () ->
                controller.getResult(LocalDateTime.now().plusDays(2), LocalDateTime.now(), 25L));
        String expectedMessage = "Дата / время начала диапазона фильтрации не может быть после даты / времени завершения";
        String actualMessage = runtimeException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}