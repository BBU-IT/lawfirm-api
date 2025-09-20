package _bbu.lawfirmapi.models.DTO.role.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(name = "role_name")
    private String roleName;
//    private Timestamp createAt;
}
