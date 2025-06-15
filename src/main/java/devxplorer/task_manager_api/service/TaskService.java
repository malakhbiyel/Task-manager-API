package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.mapper.TaskMapper;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.model.Task;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.repository.TaskRepository;
import devxplorer.task_manager_api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public Optional<TaskDTO> createTaskForCurrentUser(TaskCreateDTO dto) {
        User currentUser = getCurrentUser();
        Task task = TaskMapper.fromCreateDTO(dto, currentUser);
        Task saved = taskRepository.save(task);
        return Optional.of(TaskMapper.toDTO(saved));
    }

    public List<TaskDTO> getTasksForCurrentUser() {
        User currentUser = getCurrentUser();
        return taskRepository.findByUserId(currentUser.getId())
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        User currentUser = getCurrentUser();
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .map(TaskMapper::toDTO);
    }

    public List<TaskDTO> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status).stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> updateTask(Long id, TaskCreateDTO dto) {
        User currentUser = getCurrentUser();

        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .map(task -> {
                    task.setTitle(dto.getTitle());
                    task.setDescription(dto.getDescription());
                    task.setDueDate(dto.getDueDate());
                    task.setStatus(dto.getStatus());
                    return TaskMapper.toDTO(taskRepository.save(task));
                });
    }

    public boolean deleteTask(Long id) {
        User currentUser = getCurrentUser();
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                }).orElse(false);
    }

    public Optional<TaskDTO> updateTaskStatus(Long id, Status status) {
        User currentUser = getCurrentUser();
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId().equals(currentUser.getId()))
                .map(task -> {
                    task.setStatus(status);
                    return TaskMapper.toDTO(taskRepository.save(task));
                });
    }


}
