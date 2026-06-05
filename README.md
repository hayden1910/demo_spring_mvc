# MVC - Spring MVC + MyBatis + SQL Server

A Java web application built with Spring MVC 5, MyBatis 3, and SQL Server (Chinook schema).

## Tech Stack

| Component       | Version            |
|-----------------|--------------------|
| Java            | 8                  |
| Spring Framework| 5.3.19             |
| Spring Data     | 2.7.18             |
| MyBatis         | 3.5.13             |
| MyBatis-Spring  | 2.0.7              |
| HikariCP        | 4.0.3              |
| SQL Server      | (via mssql-jdbc)   |
| Jackson         | 2.13.5             |

## Project Structure

```
src/main/java/com/example/mvc/
├── config/
│   ├── MvcConfiguration.java       # Spring @Configuration (DI, DB, MyBatis)
│   └── mapper/
│       ├── GenreMapper.xml         # MyBatis XML for Genre
│       └── TrackMapper.xml         # MyBatis XML for Track
├── controller/
│   ├── GenreController.java        # REST controller: /api/genres
│   └── TrackController.java        # REST controller: /api/tracks
├── dao/
│   ├── IGenreDao.java              # MyBatis @Mapper interface
│   └── ITrackDao.java              # MyBatis @Mapper interface
├── dataitem/
│   ├── GenreItem.java              # POJO
│   └── TrackItem.java              # POJO
└── service/
    ├── GenreService.java
    └── TrackService.java
```

## API Endpoints

### Genre (`/api/genres`)

| Method | Endpoint             | Description         |
|--------|----------------------|---------------------|
| GET    | `/api/genres`        | List all genres     |
| GET    | `/api/genres/{id}`   | Get genre by ID     |
| POST   | `/api/genres`        | Create a genre      |
| PUT    | `/api/genres/{id}`   | Update a genre      |
| DELETE | `/api/genres/{id}`   | Delete a genre      |
| GET    | `/api/genres/search` | Search by keyword   |

### Track (`/api/tracks`)

| Method | Endpoint             | Description              |
|--------|----------------------|--------------------------|
| GET    | `/api/tracks`        | List tracks (paginated)  |
| GET    | `/api/tracks/{id}`   | Get track by ID          |
| POST   | `/api/tracks`        | Create a track           |
| PUT    | `/api/tracks/{id}`   | Update a track           |
| DELETE | `/api/tracks/{id}`   | Delete a track           |

Query params for `GET /api/tracks`: `page` (default 0), `size` (default 20).

## Database

The application connects to a SQL Server database using the **Chinook** schema (tables: `Genre`, `Track`, `Album`, etc.).

Connection settings are in `src/main/resources/server-applicationContext.properties`:

```properties
jdbc.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
jdbc.url=jdbc:sqlserver://localhost:1435;databaseName=Hayden;encrypt=true;trustServerCertificate=true
jdbc.username=sa
jdbc.password=Password@123
```

## Build & Deploy

This is a Maven WAR project, deployable to any Servlet 2.4+ container (Tomcat, Jetty, etc.).

```bash
mvn clean package
```

Deploy the generated `target/mvc.war` to your servlet container.

## Configuration

All Spring configuration is annotation-based in `MvcConfiguration.java`:

- `@ComponentScan("com.example.mvc")` discovers `@Service`, `@RestController`
- `@MapperScan("com.example.mvc.dao")` discovers MyBatis `@Mapper` interfaces
- `@PropertySource("classpath:server-applicationContext.properties")` loads DB config
- HikariCP connection pool with configurable pool size
- MyBatis XML mappers loaded from `classpath:com/example/mvc/config/mapper/*.xml`
