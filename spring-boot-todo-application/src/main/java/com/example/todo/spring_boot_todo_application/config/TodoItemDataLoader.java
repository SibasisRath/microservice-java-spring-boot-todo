package com.example.todo.spring_boot_todo_application.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.todo.spring_boot_todo_application.models.TodoItem;
import com.example.todo.spring_boot_todo_application.repositories.TodoItemRepository;

@Component
public class TodoItemDataLoader implements CommandLineRunner{
    
    private final Logger logger = LoggerFactory.getLogger(TodoItemDataLoader.class);

    @Autowired
    TodoItemRepository todoItemRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSeedData();        
    }
    private void loadSeedData() {
        if (todoItemRepository.count() == 0) {
            TodoItem todoItem1= new TodoItem("Sample Todo Item 1");
            TodoItem todoItem2= new TodoItem("Sample Todo Item 2");
            todoItemRepository.save(todoItem1);
            todoItemRepository.save(todoItem2);
            logger.info("Loading initial todo items data...");
            logger.info("Loaded Todo Items: {}", todoItemRepository.count());
            // Add initial data loading logic here
        } else {
            logger.info("Todo items data already exists. Skipping data loading.");
        }
        logger.info("Number of TodoItems: {}", todoItemRepository.count());
    }
    
}
