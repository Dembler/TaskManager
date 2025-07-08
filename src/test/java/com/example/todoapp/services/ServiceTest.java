package com.example.todoapp.services;

import com.example.todoapp.entities.TodoItem;
import com.example.todoapp.repositories.TodoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;


class ServiceTodoTest {

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private ServiceTodo serviceTodo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        List<TodoItem> mockList = Arrays.asList(new TodoItem(), new TodoItem());
        when(todoItemRepository.findAll()).thenReturn(mockList);

        List<TodoItem> result = serviceTodo.getAllTodos();

        assertEquals(2, result.size());
        verify(todoItemRepository).findAll();
    }

    @Test
    void testAddTodo() {
        TodoItem todo = new TodoItem();
        when(todoItemRepository.save(todo)).thenReturn(todo);

        TodoItem result = serviceTodo.addTodo(todo);

        assertEquals(todo, result);
        verify(todoItemRepository).save(todo);
    }

    @Test
    void testDeleteTodoById_Success() {
        Long id = 1L;
        when(todoItemRepository.existsById(id)).thenReturn(true);

        boolean result = serviceTodo.deleteTodoById(id);

        assertTrue(result);
        verify(todoItemRepository).deleteById(id);
    }

    @Test
    void testDeleteTodoById_NotFound() {
        Long id = 1L;
        when(todoItemRepository.existsById(id)).thenReturn(false);

        boolean result = serviceTodo.deleteTodoById(id);

        assertFalse(result);
        verify(todoItemRepository, never()).deleteById(id);
    }

    @Test
    void testRemoveAllTodos() {
        serviceTodo.removeAllTodos();
        verify(todoItemRepository).deleteAll();
    }

    @Test
    void testGetTodoById() {
        Long id = 1L;
        TodoItem todo = new TodoItem();
        when(todoItemRepository.findById(id)).thenReturn(Optional.of(todo));

        Optional<TodoItem> result = serviceTodo.getTodoById(id);

        assertTrue(result.isPresent());
        assertEquals(todo, result.get());
    }

    @Test
    void testUpdate_Success() {
        Long id = 1L;
        TodoItem todo = new TodoItem();
        todo.setId(id);
        when(todoItemRepository.findById(id)).thenReturn(Optional.of(todo));
        when(todoItemRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        String newTitle = "Updated";
        Date newDate = new Date();
        TodoItem result = serviceTodo.update(id, newTitle, newDate);

        assertEquals(newTitle, result.getTitle());
        assertEquals(newDate, result.getDateTodo());
    }

    @Test
    void testUpdate_NotFound() {
        Long id = 99L;
        when(todoItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceTodo.update(id, "New", new Date()));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        serviceTodo.delete(id);
        verify(todoItemRepository).deleteById(id);
    }

    @Test
    void testGetTodosForNextWeek() {
        List<TodoItem> todos = List.of(new TodoItem());
        when(todoItemRepository.findTodosForNextWeek(any(), any())).thenReturn(todos);

        List<TodoItem> result = serviceTodo.getTodosForNextWeek();

        assertEquals(1, result.size());
        verify(todoItemRepository).findTodosForNextWeek(any(), any());
    }
}

