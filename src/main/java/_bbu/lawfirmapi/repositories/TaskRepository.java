package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
