package com.varun.miniserver.http;

import com.varun.miniserver.controller.TodoController;

public class Router {
    private final TodoController c;

    public Router(TodoController c) {
        this.c = c;
    }

    public HttpResponse route(HttpRequest r)
    {
        try {
            String m = r.getMethod(), p = r.getPath();
            if (m.equals("GET") && p.equals("/todos")) return c.getTodos();
            if (m.equals("POST") && p.equals("/todos")) return c.createTodo(r.getBody());
            if (m.equals("GET") && p.matches("/todos/\\d+")) return c.getTodo(Long.parseLong(p.substring(7)));
            if (m.equals("DELETE") && p.matches("/todos/\\d+")) return c.deleteTodo(Long.parseLong(p.substring(7)));
            if (m.equals("PUT") && p.matches("/todos/\\d+"))
            {
                long id = Long.parseLong(p.substring("/todos/".length()));
                return c.updateTodo(id, r.getBody());
            }
            return new HttpResponse(404, "Not Found", "{\"error\":\"Route not found\"}");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new HttpResponse(500, "Internal Server Error", "{\"error\":\"Internal server error\"}");
        }
    }
}
