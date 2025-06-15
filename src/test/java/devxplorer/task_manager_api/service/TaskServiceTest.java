package devxplorer.task_manager_api.service;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.mapper.TaskMapper;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.model.Task;
import devxplorer.task_manager_api.model.User;
import devxplorer.task_manager_api.repository.TaskRepository;
import devxplorer.task_manager_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private TaskService taskService;

    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;
    @Mock private UserDetails userDetails;

    @BeforeEach
    void setupSecurityContext() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("john");
    }

    @Test
    void createTaskForCurrentUser_success() {
        User user = new User();
        user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        TaskCreateDTO dto = new TaskCreateDTO();
        Task task = TaskMapper.fromCreateDTO(dto, user);
        Task saved = new Task();
        when(taskRepository.save(any())).thenReturn(saved);

        Optional<TaskDTO> result = taskService.createTaskForCurrentUser(dto);

        assertTrue(result.isPresent());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getTasksForCurrentUser_success() {
        User user = new User(); user.setId(1L); user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        Task task = new Task(); task.setUser(user);
        when(taskRepository.findByUserId(1L)).thenReturn(List.of(task));

        List<TaskDTO> result = taskService.getTasksForCurrentUser();

        assertNotNull(result);
        verify(taskRepository).findByUserId(1L);
    }

    @Test
    void getTaskById_returnsTaskForCurrentUser() {
        User user = new User(); user.setId(1L); user.setUsername("john");
        Task task = new Task(); task.setUser(user);
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));

        Optional<TaskDTO> result = taskService.getTaskById(10L);

        assertTrue(result.isPresent());
    }

    @Test
    void getTaskById_returnsEmptyIfNotOwner() {
        User user = new User(); user.setId(1L); user.setUsername("john");
        User other = new User(); other.setId(2L);
        Task task = new Task(); task.setUser(other);
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));

        Optional<TaskDTO> result = taskService.getTaskById(10L);

        assertTrue(result.isEmpty());
    }

    // Add similar tests for updateTask, deleteTask, updateTaskStatus, getTasksByStatus...
}