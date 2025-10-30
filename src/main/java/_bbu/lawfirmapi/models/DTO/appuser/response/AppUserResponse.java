package _bbu.lawfirmapi.models.DTO.appuser.response;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AppUserResponse {
    private Long appUserId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private String description;
}
