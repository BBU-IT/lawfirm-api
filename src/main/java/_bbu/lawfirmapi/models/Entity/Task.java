package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.DTO.task.response.TaskResponse;
import _bbu.lawfirmapi.models.Enumerations.TaskPriority;
import _bbu.lawfirmapi.models.Enumerations.TaskStatus;
import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true , onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id" , referencedColumnName = "case_id")
    private Case legalCase;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id" , referencedColumnName = "appuser_id")
    private AppUser lawyer;
    @Column(name = "title")
    private String title;
    @Column(name = "description" , columnDefinition = " TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    public Task(Objects o, Long caseId , Long lawyerId , String title , String description ,TaskStatus status , TaskPriority taskPriority , LocalDateTime dueDate){

    }
    public TaskResponse toResponse(){
        return new TaskResponse(
                this.taskId,
                this.legalCase,
                this.lawyer,
                this.title,
                this.description,
                this.status,
                this.priority,
                this.dueDate
        );

    }

}
