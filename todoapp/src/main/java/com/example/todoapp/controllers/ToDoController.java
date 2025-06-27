package com.example.todoapp.controllers;

import com.example.todoapp.entities.TodoItem;
import com.example.todoapp.services.ServiceTodo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@Controller
public class ToDoController implements CommandLineRunner {

    private final ServiceTodo serviceTodo;

    public ToDoController(ServiceTodo serviceTodo) {
        this.serviceTodo = serviceTodo;
    }

    @GetMapping
    public String index(Model model) {
        List<TodoItem> allTodos = serviceTodo.getAllTodos();
        model.addAttribute("allTodos", allTodos);
        model.addAttribute("newTodo", new TodoItem());
        return "index";
    }

    // C — Create
    @PostMapping("/add")
    public String add(
            @RequestParam("title") String title,
            @RequestParam("dateTodo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTodo
    ) {
        TodoItem item = new TodoItem(title, dateTodo);
        serviceTodo.addTodo(item);
        return "redirect:/";
    }

    // D — Delete
    @PostMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        serviceTodo.deleteTodoById(id);
        return "redirect:/";
    }

    @PostMapping("/removeAll")
    public String removeAllItem() {
        serviceTodo.removeAllTodos();
        return "redirect:/";
    }

    // U — Update
    @PostMapping("/update/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("dateTodo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTodo
    ) {
        serviceTodo.update(id, title, dateTodo);
        return "redirect:/";
    }

    @GetMapping("/next-week")
    public String getTodosForNextWeek(Model model) {
        List<TodoItem> weekTodos = serviceTodo.getTodosForNextWeek();
        model.addAttribute("allTodos", weekTodos); // Используем ту же переменную, что в index.html
        model.addAttribute("newTodo", new TodoItem()); // Если есть форма добавления
        return "index"; // Используем index.html
    }


    @Override
    public void run(String... args) {
        serviceTodo.addTodo(new TodoItem("Задача 1", new Date()));
        serviceTodo.addTodo(new TodoItem("Задача 2", new Date()));
    }
}
