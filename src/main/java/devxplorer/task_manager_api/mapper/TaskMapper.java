package devxplorer.task_manager_api.mapper;


import devxplorer.task_manager_api.dto.TaskCreateDTO;
import devxplorer.task_manager_api.dto.TaskDTO;
import devxplorer.task_manager_api.model.Task;
import devxplorer.task_manager_api.model.User;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        if (task == null) return null;

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getUser() != null ? task.getUser().getId() : null
        );
    }

    public static Task fromCreateDTO(TaskCreateDTO dto, User user) {
        if (dto == null) return null;

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setUser(user);

        return task;
    }
}

