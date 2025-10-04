package _bbu.lawfirmapi.models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "created_at")

    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // will calls onCreate(), setting both createdAt and updatedAt
    @PrePersist
    protected void onCreateNewRole(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    //will calls onUpdate() to refresh only updatedAt
    @PreUpdate
    protected void onUpdateRole(){
        this.updatedAt = LocalDateTime.now();
    }
}
