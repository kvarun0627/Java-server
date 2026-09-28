package com.varun.miniserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.miniserver.http.HttpResponse;
import com.varun.miniserver.model.Todo;
import com.varun.miniserver.model.TodoRequest;
import com.varun.miniserver.service.TodoService;

import java.util.*;

public class TodoController
{
    private final TodoService service;

    private final ObjectMapper objectMapper;

    public TodoController(TodoService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    public HttpResponse createTodo(String body) throws Exception {

        TodoRequest request =
                objectMapper.readValue(
                        body,
                        TodoRequest.class
                );

        Todo todo =
                service.createTodo(
                        request.getTitle()
                );

        return new HttpResponse(
                201,
                "Created",
                objectMapper.writeValueAsString(todo)
        );
    }

    public HttpResponse getTodos() throws Exception
    {
        List<Todo> todos = service.getTodos();
        return new HttpResponse(
                200,
                "OK",
                objectMapper.writeValueAsString(todos)
        );
    }

    public HttpResponse getTodo(long id) throws Exception {

        Todo todo = service.getTodo(id);

        if (todo == null)
        {
            return new HttpResponse(
                    404,
                    "Not Found",
                    "{\"error\":\"Todo not found\"}"
            );
        }

        return new HttpResponse(
                200,
                "OK",
                objectMapper.writeValueAsString(todo)
        );
    }

    public HttpResponse deleteTodo(long id) throws Exception {
        return service.deleteTodo(id) ? new HttpResponse(200, "OK", "{\"message\":\"Todo deleted\"}") : new HttpResponse(404, "Not Found", "{\"error\":\"Todo not found\"}");
    }

    public HttpResponse updateTodo(Long id, String body) throws Exception
    {
        TodoRequest request = objectMapper.readValue(
                        body,
                        TodoRequest.class
                );

        Todo todo = service.updateTodo(
                        id,
                        request.getTitle(),
                        request.isCompleted()
                );

        if (todo == null)
        {
            return new HttpResponse(
                    404,
                    "Not Found",
                    "{\"error\":\"Todo not found\"}"
            );
        }

        return new HttpResponse(
                200,
                "OK",
                objectMapper.writeValueAsString(todo)
        );
    }
}
