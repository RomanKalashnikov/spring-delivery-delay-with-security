package ru.kalasnikov.delivery.task.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
//TODO тестовые данные, необходимо убрать

    @Id
    private Long id;

    @Column
    private Long orderNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createTime;

    @Column
    private boolean completed;
}
