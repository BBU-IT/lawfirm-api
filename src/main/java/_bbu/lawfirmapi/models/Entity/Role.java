package _bbu.lawfirmapi.models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "role" , cascade = CascadeType.ALL)
//    @ToString.Exclude
//    private List<AppUser> users;

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
