package ru.kalasnikov.delivery.task.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.kalasnikov.delivery.task.entity.Task;
import ru.kalasnikov.delivery.task.mapper.RowTaskMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class TaskRepositoryImpl implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final AtomicLong idCount = new AtomicLong(1);

    @Autowired
    public TaskRepositoryImpl(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @PostConstruct
    void postInit() {
        try {
            jdbcTemplate.execute("drop table TASK;");
        } catch (DataAccessException e) {
            log.warn("Таблица ещё не создана");
        }
        try {
            jdbcTemplate.execute("create table TASK(" +
                     "id INTEGER primary key , order_Number INTEGER , create_Time TIMESTAMP ,completed CHAR(1)) ");
        } catch (DataAccessException e) {
            System.out.println(4);
        }
    }

    @Override
    public List<Task> findTasksByCreateTimeIsBetweenAndOrderNumber(LocalDateTime start, LocalDateTime end, Long orderNumber) {
        if (start == null && end == null && orderNumber == null) {
            return getAllDataFromTable();
        }
        if (orderNumber == null) {
            return getDataFromTableWithATimeFilter(start, end);
        }
        if (orderNumber != null) {
            return getDataFromTableWithTimeFilterAndOrderNumber(start, end, orderNumber);
        }
        return new ArrayList<>();
    }

    private List<Task> getAllDataFromTable() {
        return jdbcTemplate.query("SELECT * FROM task", new RowTaskMapper());
    }

    private List<Task> getDataFromTableWithATimeFilter(LocalDateTime start, LocalDateTime end) {
        return jdbcTemplate.query("SELECT * FROM task WHERE task.create_time  BETWEEN ? AND ? ",
                new Object[]{start, end},
                new RowTaskMapper());
    }

    private List<Task> getDataFromTableWithTimeFilterAndOrderNumber(LocalDateTime start, LocalDateTime end, Long orderNumber) {
        return jdbcTemplate.query("SELECT * FROM task WHERE (task.create_time BETWEEN ? AND ?) AND task.order_number = ?",
                new Object[]{start, end, orderNumber},
                new RowTaskMapper());
    }

    @Override
    public Task saveNewTaskIntoTable(Task task) {

        int status = getInsertStatusIntoTableTask(configurateTaskBeforeSave(task));

        if (status <= 0) {
            throw new RuntimeException("При добавлении задачи в таблицу возникла ошибка");
        }
        return task;
    }

    private Task configurateTaskBeforeSave(Task task) {
        task.setId(idCount.getAndIncrement());
        task.setCreateTime(LocalDateTime.now());
        return task;
    }

    private int getInsertStatusIntoTableTask(Task task) {
        return jdbcTemplate.update("INSERT INTO TASK VALUES (?, ?, ?, ?)",
                task.getId(),
                task.getOrderNumber(),
                task.getCreateTime(),
                task.isCompleted() ? "1" : "0");

    }
}
