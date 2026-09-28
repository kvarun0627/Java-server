# Mini TCP HTTP Todo Server

Stage 1-3 project: raw TCP server, HTTP parsing/routing, PostgreSQL JDBC, and a simple custom connection pool. No Spring Boot, Tomcat, Hibernate/JPA, or HikariCP.

## Requirements
- Java 17+
- Maven
- PostgreSQL

## Database
```sql
CREATE DATABASE todo_db;

CREATE TABLE todos (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE
);
```

Update the PostgreSQL password in `Main.java`, then run:

```bash
mvn compile
mvn exec:java -Dexec.mainClass=com.varun.miniserver.Main
```

If the exec plugin is not configured, run `Main` directly from IntelliJ, or use your IDE's Maven/Java run configuration.

## Endpoints
- `POST /todos` body: `{"title":"Learn TCP servers"}`
- `GET /todos`
- `GET /todos/{id}`
- `DELETE /todos/{id}`
