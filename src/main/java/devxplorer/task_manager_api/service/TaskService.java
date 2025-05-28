package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.exception.ResourceNotFoundException;
import devxplorer.task_manager_api.mapper.TaskMapper;
import devxplorer.task_manager_api.model.Task;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public TaskDTO createTask(Long userId, TaskCreateDTO dto) {
        User user = userService.getUserById(userId);
        Task task = TaskMapper.fromCreateDTO(dto, user);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDTO(savedTask);
    }

    public List<TaskDTO> getTasksByUserId(Long userId) {
        userService.getUserById(userId);
        return taskRepository.findByUserId(userId).stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        return TaskMapper.toDTO(task);
    }

    // Tu peux ajouter d'autres méthodes comme updateTask, deleteTask, etc.
}
