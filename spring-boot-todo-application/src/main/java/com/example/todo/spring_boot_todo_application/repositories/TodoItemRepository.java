package com.example.todo.spring_boot_todo_application.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.todo.spring_boot_todo_application.models.TodoItem;

public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    
}