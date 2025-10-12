package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.Enumerations.TaskPriority;
import _bbu.lawfirmapi.models.Enumerations.TaskStatus;
import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id" , referencedColumnName = "case_id")
    private Case aCase ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id" , referencedColumnName = "appuser_id")
    private AppUser user;
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
}
