package com.varun.miniserver.service;

import com.varun.miniserver.model.Todo;
import com.varun.miniserver.repository.TodoRepository;

import java.util.List;

public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo createTodo(String title) throws Exception {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty");
        return repository.create(new Todo(title, false));
    }

    public List<Todo> getTodos() throws Exception {
        return repository.findAll();
    }

    public Todo getTodo(long id) throws Exception {
        return repository.findById(id);
    }

    public boolean deleteTodo(long id) throws Exception {
        return repository.delete(id);
    }

    public Todo updateTodo(
            long id,
            String title,
            boolean completed
    ) throws Exception {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Title cannot be empty"
            );
        }

        return repository.update(
                id,
                title,
                completed
        );
    }
}
