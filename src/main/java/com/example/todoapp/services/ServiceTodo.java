package com.example.todoapp.services;

import com.example.todoapp.entities.TodoItem;
import com.example.todoapp.repositories.TodoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Calendar;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class ServiceTodo {

    private final TodoItemRepository todoItemRepository;

    public List<TodoItem> getAllTodos() {
        return todoItemRepository.findAll();
    }

    public TodoItem addTodo(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    public boolean deleteTodoById(Long id) {
        if (todoItemRepository.existsById(id)) {
            todoItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void removeAllTodos() {
        todoItemRepository.deleteAll();
    }

    public Optional<TodoItem> getTodoById(Long id) {
        return todoItemRepository.findById(id);
    }

    // U — Update
    public TodoItem update(Long id, String newTitle, Date newDate) {
        Optional<TodoItem> optionalTodo = todoItemRepository.findById(id);
        if (optionalTodo.isPresent()) {
            TodoItem todoItem = optionalTodo.get();
            todoItem.setTitle(newTitle);
            todoItem.setDateTodo(newDate);
            return todoItemRepository.save(todoItem);
        } else {
            throw new RuntimeException("Задача с id " + id + " не найдена");
        }
    }

    public void delete(Long id) {
        todoItemRepository.deleteById(id);
    }

    public List<TodoItem> getTodosForNextWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date nextWeek = calendar.getTime();

        return todoItemRepository.findTodosForNextWeek(today, nextWeek);
    }

}
