package com.example.todoapp.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Tasks")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_todo")
    private Date dateTodo;

    public TodoItem() {
    }

    public TodoItem(String title, Date dateTodo) {
        this.title = title;
        this.dateTodo = dateTodo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateTodo() {
        return dateTodo;
    }

    public void setDateTodo(Date dateTodo) {
        this.dateTodo = dateTodo;
    }
}
