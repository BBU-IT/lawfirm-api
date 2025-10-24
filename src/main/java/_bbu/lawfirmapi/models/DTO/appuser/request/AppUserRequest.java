package _bbu.lawfirmapi.models.DTO.appuser.request;
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
    private Integer roleId;
    private String description;

}
