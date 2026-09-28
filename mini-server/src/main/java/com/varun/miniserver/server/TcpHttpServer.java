package com.varun.miniserver.server;

import com.varun.miniserver.http.Router;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TcpHttpServer {
    private final int port;
    private final Router router;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private ServerSocket serverSocket;

    public TcpHttpServer(int port, Router router) {
        this.port = port;
        this.router = router;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (!serverSocket.isClosed()) {
            Socket s = serverSocket.accept();
            threadPool.execute(new HttpConnectionHandler(s, router));
        }
    }

    public void stop() throws IOException {
        if (serverSocket != null)
            serverSocket.close();
        threadPool.shutdown();
        System.out.println("Server stopped.");
    }
}
