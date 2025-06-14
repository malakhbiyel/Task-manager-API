package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Créer une nouvelle tâche pour l'utilisateur connecté",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tâche créée avec succès",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
            }
    )
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.createTaskForCurrentUser(taskCreateDTO)
                .map(task -> new ResponseEntity<>(task, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Récupérer toutes les tâches de l'utilisateur connecté",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des tâches récupérée",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class, type = "array"))),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasksForCurrentUser() {
        return ResponseEntity.ok(taskService.getTasksForCurrentUser());
    }

    @Operation(
            summary = "Récupérer les tâches par statut",
            parameters = @Parameter(name = "status", description = "Statut de la tâche", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des tâches filtrées par statut",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class, type = "array"))),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
            }
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @Operation(
            summary = "Mettre à jour une tâche par son ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tâche mise à jour avec succès",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskCreateDTO dto) {
        return taskService.updateTask(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Supprimer une tâche par son ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tâche supprimée avec succès"),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Mettre à jour le statut d'une tâche par son ID",
            parameters = @Parameter(name = "status", description = "Nouveau statut de la tâche", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Statut de la tâche mis à jour avec succès",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié"),
                    @ApiResponse(responseCode = "404", description = "Tâche non trouvée")
            }
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return taskService.updateTaskStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

