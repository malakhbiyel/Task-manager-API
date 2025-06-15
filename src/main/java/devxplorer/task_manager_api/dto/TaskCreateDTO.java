package devxplorer.task_manager_api.dto;

import devxplorer.task_manager_api.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Données nécessaires à la création d'une tâche")
public class TaskCreateDTO {

    @Schema(description = "Titre de la tâche", example = "Faire les courses")
    private String title;

    @Schema(description = "Description de la tâche", example = "Acheter du lait, du pain, etc.")
    private String description;

    @Schema(description = "Date limite pour accomplir la tâche", example = "2025-06-20T17:00:00")
    private LocalDateTime dueDate;

    @Schema(description = "Statut actuel de la tâche", example = "EN_COURS")
    private Status status;

    public TaskCreateDTO() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}

