package _bbu.lawfirmapi.models.DTO.role.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    @Column(name = "role_name")
    private String roleName;
}
