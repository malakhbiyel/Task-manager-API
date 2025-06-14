package devxplorer.task_manager_api.dto;

import devxplorer.task_manager_api.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Représentation complète d'une tâche")
public class TaskDTO {

    @Schema(description = "Identifiant unique de la tâche", example = "1")
    private Long id;

    @Schema(description = "Titre de la tâche", example = "Faire les courses")
    private String title;

    @Schema(description = "Description de la tâche", example = "Acheter du lait et des œufs")
    private String description;

    @Schema(description = "Statut actuel", example = "EN_COURS")
    private Status status;

    @Schema(description = "Date limite", example = "2025-06-20T17:00:00")
    private LocalDateTime dueDate;

    @Schema(description = "Identifiant de l'utilisateur assigné", example = "10")
    private Long userId;

    public TaskDTO() {}

    public TaskDTO(Long id, String title, String description, Status status, LocalDateTime dueDate, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.userId = userId;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
