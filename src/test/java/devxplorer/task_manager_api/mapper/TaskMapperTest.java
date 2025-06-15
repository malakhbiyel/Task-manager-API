package devxplorer.task_manager_api.mapper;

import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.model.Task;
import devxplorer.task_manager_api.model.User;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    @Test
    void toDTO_returnsTaskDTO_whenTaskNotNull() {
        User user = new User();
        user.setId(42L);
        Task task = new Task();
        task.setId(1L);
        task.setTitle("My Task");
        task.setDescription("Desc");
        task.setStatus(Status.PENDING);
        task.setDueDate(LocalDateTime.now());
        task.setUser(user);

        TaskDTO dto = TaskMapper.toDTO(task);

        assertNotNull(dto);
        assertEquals(task.getId(), dto.getId());
        assertEquals(task.getUser().getId(), dto.getUserId());
    }

    @Test
    void toDTO_returnsNull_whenTaskIsNull() {
        assertNull(TaskMapper.toDTO(null));
    }

    @Test
    void fromCreateDTO_returnsTask_whenDTOProvided() {
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTitle("Title");
        dto.setDescription("Desc");
        dto.setStatus(Status.PENDING);
        dto.setDueDate(LocalDateTime.now());
        User user = new User();

        Task task = TaskMapper.fromCreateDTO(dto, user);

        assertNotNull(task);
        assertEquals(dto.getTitle(), task.getTitle());
        assertEquals(user, task.getUser());
    }

    @Test
    void fromCreateDTO_returnsNull_whenDTONull() {
        assertNull(TaskMapper.fromCreateDTO(null, new User()));
    }
}