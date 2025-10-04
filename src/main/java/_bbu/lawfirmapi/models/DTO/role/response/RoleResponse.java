package _bbu.lawfirmapi.models.DTO.role.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private Integer roleId;
    private String roleName;
}
