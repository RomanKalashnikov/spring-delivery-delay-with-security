package ru.kalasnikov.delivery.task.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kalasnikov.delivery.task.entity.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskRepositoryImplTest {
    private final TaskRepository repository;

    @Autowired
    TaskRepositoryImplTest(TaskRepository repository) {
        this.repository = repository;
    }

    @Test
    void whenReturnEmptyList_ThenAssertionSucceeds() {
        List<Task> list = repository.findTasksByCreateTimeIsBetweenAndOrderNumber(null, null, null);

        assertEquals(new ArrayList<>().size(), list.size());

    }

    @Test
    void whenNotNullFieldsAfterSave() {
        Task testTask = new Task();
        testTask.setId(14L);
        testTask.setCreateTime(LocalDateTime.now());
        testTask.setCompleted(true);
        Task task = repository.saveNewTaskIntoTable(testTask);
        assertNotNull(task);
        assertNotNull(task.getId());
    }
    @Test
    void whenEqualsFieldsBeforeAndAfterSave(){
        LocalDateTime dateTimeNow = LocalDateTime.now();
        Task testTask = new Task();
        testTask.setOrderNumber(14L);
        testTask.setCreateTime(dateTimeNow);
        testTask.setCompleted(true);
        Task task = repository.saveNewTaskIntoTable(testTask);
        assertEquals(14L, task.getOrderNumber());
        assertEquals(dateTimeNow,task.getCreateTime());
        assertTrue(task.isCompleted());
    }

    @Test
    void whenTransferToSaveNullThrownNullPointerException_ThenAssertionSucceeds() {
        assertThrows(NullPointerException.class, () -> repository.saveNewTaskIntoTable(null));


    }


}