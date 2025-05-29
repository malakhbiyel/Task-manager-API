package devxplorer.task_manager_api.dto;

import devxplorer.task_manager_api.model.Status;

import java.time.LocalDateTime;

public class TaskCreateDTO {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Status status;

    public TaskCreateDTO() {}

    public TaskCreateDTO(String title, String description, LocalDateTime dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}

