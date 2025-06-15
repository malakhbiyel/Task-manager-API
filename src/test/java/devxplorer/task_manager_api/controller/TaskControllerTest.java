package devxplorer.task_manager_api.controller;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock TaskService taskService;
    @InjectMocks TaskController taskController;

    @Test
    void createTask_returnsCreated() {
        TaskCreateDTO dto = new TaskCreateDTO();
        TaskDTO taskDTO = new TaskDTO();
        when(taskService.createTaskForCurrentUser(dto)).thenReturn(Optional.of(taskDTO));

        ResponseEntity<TaskDTO> response = taskController.createTask(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
    }

    @Test
    void createTask_returnsNotFound() {
        when(taskService.createTaskForCurrentUser(any())).thenReturn(Optional.empty());
        ResponseEntity<TaskDTO> response = taskController.createTask(new TaskCreateDTO());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getTasksForCurrentUser_returnsTasks() {
        List<TaskDTO> tasks = List.of(new TaskDTO());
        when(taskService.getTasksForCurrentUser()).thenReturn(tasks);
        ResponseEntity<List<TaskDTO>> response = taskController.getTasksForCurrentUser();
        assertEquals(tasks, response.getBody());
    }

    @Test
    void getTasksByStatus_returnsTasks() {
        List<TaskDTO> tasks = List.of(new TaskDTO());
        when(taskService.getTasksByStatus(Status.PENDING)).thenReturn(tasks);
        ResponseEntity<List<TaskDTO>> response = taskController.getTasksByStatus(Status.PENDING);
        assertEquals(tasks, response.getBody());
    }

    @Test
    void updateTask_returnsOk() {
        TaskDTO taskDTO = new TaskDTO();
        when(taskService.updateTask(eq(1L), any())).thenReturn(Optional.of(taskDTO));
        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, new TaskCreateDTO());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
    }

    @Test
    void updateTask_returnsNotFound() {
        when(taskService.updateTask(eq(1L), any())).thenReturn(Optional.empty());
        ResponseEntity<TaskDTO> response = taskController.updateTask(1L, new TaskCreateDTO());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteTask_returnsNoContent() {
        when(taskService.deleteTask(1L)).thenReturn(true);
        ResponseEntity<Void> response = taskController.deleteTask(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteTask_returnsNotFound() {
        when(taskService.deleteTask(1L)).thenReturn(false);
        ResponseEntity<Void> response = taskController.deleteTask(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateStatus_returnsOk() {
        TaskDTO taskDTO = new TaskDTO();
        when(taskService.updateTaskStatus(eq(1L), eq(Status.COMPLETED))).thenReturn(Optional.of(taskDTO));
        ResponseEntity<TaskDTO> response = taskController.updateStatus(1L, Status.COMPLETED);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskDTO, response.getBody());
    }

    @Test
    void updateStatus_returnsNotFound() {
        when(taskService.updateTaskStatus(eq(1L), eq(Status.COMPLETED))).thenReturn(Optional.empty());
        ResponseEntity<TaskDTO> response = taskController.updateStatus(1L, Status.COMPLETED);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}