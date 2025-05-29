package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.mapper.TaskMapper;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.model.Task;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.repository.TaskRepository;
import devxplorer.task_manager_api.repository.UserRepository;
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

    public Optional<TaskDTO> createTask(Long userId, TaskCreateDTO dto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) return Optional.empty();

        Task task = TaskMapper.fromCreateDTO(dto, user.get());
        Task saved = taskRepository.save(task);
        return Optional.of(TaskMapper.toDTO(saved));
    }

    public List<TaskDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(TaskMapper::toDTO);
    }

    public List<TaskDTO> getTasksByStatus(Status status) {
        return taskRepository.findByStatus(status).stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

}
