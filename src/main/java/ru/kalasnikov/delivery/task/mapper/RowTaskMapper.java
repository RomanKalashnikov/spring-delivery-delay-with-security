package ru.kalasnikov.delivery.task.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.kalasnikov.delivery.task.entity.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowTaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();

        if (resultSet.getInt("id") != 0) {
            task.setId((long) resultSet.getInt("id"));
        }
        task.setOrderNumber((long) resultSet.getInt("order_number"));
        task.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());
        task.setCompleted(resultSet.getInt("completed") == 1);
        return task;
    }
}
