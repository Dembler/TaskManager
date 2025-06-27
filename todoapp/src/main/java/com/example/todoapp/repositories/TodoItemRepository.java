package com.example.todoapp.repositories;

import com.example.todoapp.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    @Query("SELECT t FROM TodoItem t WHERE t.dateTodo BETWEEN :startDate AND :endDate")
    List<TodoItem> findTodosForNextWeek(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
