package devxplorer.task_manager_api.repository;

import devxplorer.task_manager_api.model.Status;
import devxplorer.task_manager_api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByStatus(Status status);
}
