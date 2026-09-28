package com.varun.miniserver;

import com.varun.miniserver.controller.TodoController;
import com.varun.miniserver.db.ConnectionPool;
import com.varun.miniserver.http.Router;
import com.varun.miniserver.repository.TodoRepository;
import com.varun.miniserver.server.TcpHttpServer;
import com.varun.miniserver.service.TodoService;

public class Main {
    public static void main(String[] args) throws Exception {
        String dbUrl = "jdbc:postgresql://localhost:5432/todo_db";
        String username = "postgres";
        String password = "postgres123";
        ConnectionPool pool = new ConnectionPool(dbUrl, username, password, 5);
        TodoRepository repo = new TodoRepository(pool);
        TodoService service = new TodoService(repo);
        TodoController controller = new TodoController(service);
        Router router = new Router(controller);
        TcpHttpServer server = new TcpHttpServer(8080, router);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            pool.shutdown();
        }));
        server.start();
    }
}
