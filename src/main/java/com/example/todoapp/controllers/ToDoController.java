package com.example.todoapp.controllers;

import com.example.todoapp.entities.TodoItem;
import com.example.todoapp.services.ServiceTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class ToDoController {

    private final ServiceTodo serviceTodo;

    @GetMapping
    public ResponseEntity<List<TodoItem>> getAllTodos() {
        return ResponseEntity.ok(serviceTodo.getAllTodos());
    }

    @PostMapping("/add")
    public ResponseEntity<TodoItem> addTodo(
            @RequestParam("title") String title,
            @RequestParam("dateTodo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTodo
    ) {
        TodoItem item = new TodoItem(title, dateTodo);
        TodoItem saved = serviceTodo.addTodo(item);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable("id") Long id) {
        boolean deleted = serviceTodo.deleteTodoById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<Void> removeAllItem() {
        serviceTodo.removeAllTodos();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TodoItem> updateTodo(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("dateTodo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTodo
    ) {
        try {
            TodoItem updated = serviceTodo.update(id, title, dateTodo);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/next-week")
    public ResponseEntity<List<TodoItem>> getTodosForNextWeek() {
        return ResponseEntity.ok(serviceTodo.getTodosForNextWeek());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoById(@PathVariable("id") Long id) {
        Optional<TodoItem> optionalTodo = serviceTodo.getTodoById(id);
        return optionalTodo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

