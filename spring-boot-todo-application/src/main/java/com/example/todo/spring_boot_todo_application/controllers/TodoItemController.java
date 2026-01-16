package com.example.todo.spring_boot_todo_application.controllers;
import java.time.Instant;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todo.spring_boot_todo_application.models.TodoItem;
import com.example.todo.spring_boot_todo_application.repositories.TodoItemRepository;

import jakarta.validation.Valid;

@Controller
public class TodoItemController {
    private final Logger logger = LoggerFactory.getLogger(TodoItemController.class);
    @Autowired
    private TodoItemRepository todoItemRepository;

     @GetMapping("/")
    public ModelAndView index() {
        logger.info("request to GET index");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("todoItems", todoItemRepository.findAll());
        modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
        return modelAndView;
    }

    @GetMapping("/create-todo")
    public String showAddForm(Model model) {
        model.addAttribute("todoItem", new TodoItem());
        return "add-todo-item";
    }

    // @GetMapping("/todo/edit/{id}")
    // public String showUpdateForm(@PathVariable("id") long id, Model model) {
    //     TodoItem item = todoItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
    //     model.addAttribute("todoItem", item);
    //     return "update-todo-item";
    // }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result) {
        if (result.hasErrors()) {
            return "add-todo-item";
        }

        todoItem.setCreatedDate(Instant.now());
        todoItem.setModifiedDate(Instant.now());
        todoItemRepository.save(todoItem);
        return "redirect:/";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") long id, @Valid TodoItem todoItem, BindingResult result) {
        if (result.hasErrors()) {
            todoItem.setId(id);
            return "update-todo-item";
        }
        TodoItem existingItem = todoItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        existingItem.setDescription(todoItem.getDescription());
        existingItem.setComplete(todoItem.isComplete());
        existingItem.setModifiedDate(Instant.now());
    
        todoItemRepository.save(existingItem);
        return "redirect:/";
    }
}
