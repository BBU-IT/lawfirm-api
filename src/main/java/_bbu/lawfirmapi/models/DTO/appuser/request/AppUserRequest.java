package _bbu.lawfirmapi.models.DTO.appuser.request;
import _bbu.lawfirmapi.models.Entity.Department;
import _bbu.lawfirmapi.models.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserRequest {
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private Integer roleId;        // ✅ just ID
    private Long departmentId;
    private String description;

}
