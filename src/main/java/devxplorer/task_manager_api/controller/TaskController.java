package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.service.TaskService;
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

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.createTaskForCurrentUser(taskCreateDTO)
                .map(task -> new ResponseEntity<>(task, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasksForCurrentUser() {
        return ResponseEntity.ok(taskService.getTasksForCurrentUser());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskCreateDTO dto) {
        return taskService.updateTask(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return taskService.updateTaskStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}


