package com.example.todoapp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "Tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_todo")
    Date dateTodo;

    // Если нужен конструктор только с title и dateTodo:
    public TodoItem(String title, Date dateTodo) {
        this.title = title;
        this.dateTodo = dateTodo;
    }
}

