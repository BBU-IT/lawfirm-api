package _bbu.lawfirmapi.services.task.implement;

import _bbu.lawfirmapi.models.DTO.task.request.TaskRequest;
import _bbu.lawfirmapi.models.DTO.task.response.TaskResponse;
import _bbu.lawfirmapi.models.Entity.Task;
import _bbu.lawfirmapi.repositories.TaskRepository;
import _bbu.lawfirmapi.services.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepo;


    @Override
    public Page<Task> getTaskList(Pageable pageable, Integer requestedPage) {
        return null;
    }

    @Override
    public TaskResponse createNewTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public TaskResponse modifiedTaskById(Long taskId, TaskRequest taskRequest) {
        return null;
    }

    @Override
    public Void removeTaskById(Long taskId) {
        return null;
    }
}
