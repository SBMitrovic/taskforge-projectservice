# taskforge-projectservice

> This service is part of the **TaskForge** microservices architecture — a project management platform inspired by Jira. Full project coming soon.

## About

`taskforge-projectservice` handles all project and task management for the TaskForge platform. It provides a REST API for creating and managing projects, tasks, and project members, with role-based access control via JWT authentication.

**Endpoints:**

Projects:
- `GET /api/projects` — get all projects (ADMIN only)
- `GET /api/projects/my` — get current user's projects
- `GET /api/projects/user/{userId}` — get projects by user (ADMIN only)
- `GET /api/projects/{id}` — get project by ID
- `POST /api/projects` — create a project
- `PUT /api/projects/{id}` — update a project
- `DELETE /api/projects/{id}` — delete a project (ADMIN only)
- `POST /api/projects/{id}/members` — add a member to a project
- `DELETE /api/projects/{id}/members/{userId}` — remove a member from a project

Tasks:
- `GET /api/tasks/project/{projectId}` — get all tasks for a project
- `GET /api/tasks/my` — get current user's tasks
- `GET /api/tasks/{id}` — get task by ID
- `POST /api/tasks` — create a task
- `PUT /api/tasks/{id}` — update a task
- `PATCH /api/tasks/{id}/status` — update task status
- `DELETE /api/tasks/{id}` — delete a task (ADMIN only)

## Tech Stack

- Java 21, Spring Boot 3.4.1
- Spring Security + JWT (jjwt 0.12.3)
- Spring Data JPA + MySQL 8
- Lombok, Gradle

## Running Locally

**Prerequisites:** Java 21, Docker

**1. Start the database** (from [taskforge-infra](https://github.com/SBMitrovic/taskforge-infra)):
```bash
docker-compose up -d
```

**2. Run the service:**
```bash
./gradlew bootRun
```

Service runs on `http://localhost:1112`

> All endpoints require a valid JWT token issued by [taskforge-authservice](https://github.com/SBMitrovic/taskforge-authservice).

## Microservices Architecture — TaskForge

```
taskforge-infra              → Docker, database, infrastructure
taskforge-authservice        → Authentication & authorization
taskforge-projectservice     → Projects & tasks (this service)
taskforge-frontend           → Frontend (coming soon)
```
