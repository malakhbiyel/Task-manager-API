package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long userId, @RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.createTask(userId, taskCreateDTO)
                .map(task -> new ResponseEntity<>(task, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUserId(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }
}

