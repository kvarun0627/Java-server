package com.varun.miniserver.repository;

import com.varun.miniserver.db.ConnectionPool;
import com.varun.miniserver.model.Todo;

import java.sql.*;
import java.util.*;

public class TodoRepository {
    private final ConnectionPool pool;

    public TodoRepository(ConnectionPool pool) {
        this.pool = pool;
    }

    public Todo create(Todo todo) throws Exception {
        Connection c = null;
        try {
            c = pool.getConnection();
            try (
                    PreparedStatement s = c.prepareStatement("INSERT INTO todos(title,completed) VALUES (?,?) RETURNING id")
            ) {
                s.setString(1, todo.getTitle());
                s.setBoolean(2, todo.isCompleted());
                try (ResultSet r = s.executeQuery()) {
                    if (r.next()) todo.setId(r.getLong("id"));
                }
                return todo;
            }
        } finally {
            pool.releaseConnection(c);
        }
    }

    public List<Todo> findAll() throws Exception {
        Connection c = null;
        try {
            c = pool.getConnection();
            List<Todo> out = new ArrayList<>();
            try (PreparedStatement s = c.prepareStatement("SELECT id,title,completed FROM todos ORDER BY id"); ResultSet r = s.executeQuery()) {
                while (r.next()) out.add(new Todo(r.getLong("id"), r.getString("title"), r.getBoolean("completed")));
            }
            return out;
        } finally {
            pool.releaseConnection(c);
        }
    }

    public Todo findById(long id) throws Exception {
        Connection c = null;
        try {
            c = pool.getConnection();
            try (PreparedStatement s = c.prepareStatement("SELECT id,title,completed FROM todos WHERE id=?")) {
                s.setLong(1, id);
                try (ResultSet r = s.executeQuery()) {
                    if (!r.next()) return null;
                    return new Todo(r.getLong("id"), r.getString("title"), r.getBoolean("completed"));
                }
            }
        } finally {
            pool.releaseConnection(c);
        }
    }

    public boolean delete(long id) throws Exception {
        Connection c = null;
        try {
            c = pool.getConnection();
            try (PreparedStatement s = c.prepareStatement("DELETE FROM todos WHERE id=?")) {
                s.setLong(1, id);
                return s.executeUpdate() > 0;
            }
        } finally {
            pool.releaseConnection(c);
        }
    }

    public Todo update(long id, String title, boolean completed) throws Exception
    {

        Connection connection = null;

        String sql = """
            UPDATE todos
            SET title = ?, completed = ?
            WHERE id = ?
            """;

        try {

            connection = pool.getConnection();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, title);
                statement.setBoolean(2, completed);
                statement.setLong(3, id);

                int rowsUpdated =
                        statement.executeUpdate();

                if (rowsUpdated == 0) {
                    return null;
                }

                return findById(id);
            }

        } finally {

            pool.releaseConnection(connection);
        }
    }
}
