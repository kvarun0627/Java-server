package com.varun.miniserver.db;

import java.sql.*;
import java.util.concurrent.*;

public class ConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final String url, username, password;

    public ConnectionPool(String url, String username, String password, int poolSize) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        pool = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) pool.offer(createConnection());
        System.out.println("Connection pool initialized with " + poolSize + " connections.");
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() throws InterruptedException {
        Connection c = pool.take();
        System.out.println("Connection borrowed. Available: " + pool.size());
        return c;
    }

    public void releaseConnection(Connection c) {
        if (c != null) pool.offer(c);
        System.out.println("Connection returned. Available: " + pool.size());
    }

    public void shutdown() {
        while (!pool.isEmpty()) {
            try {
                Connection c = pool.poll();
                if (c != null) c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Connection pool shutdown.");
    }
}
