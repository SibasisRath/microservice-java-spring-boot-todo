package com.example.todo.spring_boot_todo_application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.todo.spring_boot_todo_application.models.TodoItem;
import com.example.todo.spring_boot_todo_application.repositories.TodoItemRepository;

@Controller
public class TodoFormController {
    private final Logger logger = LoggerFactory.getLogger(TodoFormController.class);
    @Autowired
    private TodoItemRepository todoItemRepository;

    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable("id") long id, Model model) {
        TodoItem todoItem = todoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo item Id:" + id));
        model.addAttribute("todoItem", todoItem);
        logger.info("Accessed the edit todo form");
        return "update-todo-item";
    }

     @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") long id) {
        TodoItem todoItem = todoItemRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));
    
        todoItemRepository.delete(todoItem);
        return "redirect:/";
    }
}
