# TASK MANAGER API

<p align="center">
  <img alt="last commit" src="https://img.shields.io/github/last-commit/malakhbiyel/Task-manager-API?logo=github">
  <img alt="commit activity" src="https://img.shields.io/github/commit-activity/m/malakhbiyel/Task-manager-API?color=blue">
  <img alt="java" src="https://img.shields.io/badge/java-99.6%25-blue?logo=java">
  <img alt="languages" src="https://img.shields.io/github/languages/count/malakhbiyel/Task-manager-API?color=blue">
</p>

---

A robust, efficient, and scalable RESTful API for managing tasks, built with Java. This project provides endpoints for creating, updating, deleting, and retrieving tasks, making it ideal for integration into productivity apps, to-do lists, or project management solutions.

## 🚀 Features

- **CRUD Operations:** Create, read, update, and delete tasks with simple HTTP requests.
- **Filtering & Sorting:** Retrieve tasks by status.
- **User Authentication:** Secure endpoints (JWT authentication).
- **API Documentation:** Interactive docs (e.g., Swagger/OpenAPI) for quick testing and integration.
- **Dockerized:** Easily deployable using Docker.
- **Continuous Integration (CI):** Automated testing and build pipelines using GitHub Actions.
- **Unit Testing:** Reliable, isolated unit tests using JUnit and Mockito ensure code quality and correctness.

---

## 🏗️ Tech Stack

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white">
  <img alt="Swagger" src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black">
  <img alt="Docker" src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img alt="GitHub Actions" src="https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white">
  <img alt="JUnit" src="https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white">
  <img alt="Mockito" src="https://img.shields.io/badge/Mockito-4CAF50?style=for-the-badge&logo=mockito&logoColor=white">
</p>

---

## 📦 Getting Started

### Prerequisites

- Java 17+
- Maven
- Docker (optional, for containerized deployment)

### Clone the repository

```bash
git clone https://github.com/malakhbiyel/Task-manager-API.git
cd Task-manager-API
```

### Running Locally

#### Using Maven

```bash
./mvnw spring-boot:run
```

#### Using Docker

```bash
docker build -t task-manager-api .
docker run -p 8080:8080 task-manager-api
```

---

## API Documentation

Access interactive API docs at:  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ⚙️ Continuous Integration (CI)

This project uses GitHub Actions for continuous integration.

- Every push and pull request triggers automated builds and tests to ensure code quality.
- Easily extend the workflow to add more checks or deployment steps.
- You can find the workflow configuration in the `.github/workflows/` directory.

---

## 🧪 Testing

This project includes unit tests written with **JUnit 5** and **Mockito** for mocking dependencies.

- Run all tests using Maven:
  ```bash
  ./mvnw test
  ```
- Test classes are located under `src/test/java/`.

---

## 🧑‍💻 Example API Endpoints

- `GET /api/tasks` — List all tasks
- `POST /api/tasks` — Create a new task
- `GET /api/tasks/{id}` — Get a task by ID
- `PUT /api/tasks/{id}` — Update a task
- `DELETE /api/tasks/{id}` — Delete a task

---

## 🚦 Contributing

Contributions are welcome!  
Please open an issue or pull request to discuss your ideas or report bugs.

---

Made with ❤️ by [malakhbiyel](https://github.com/malakhbiyel)
