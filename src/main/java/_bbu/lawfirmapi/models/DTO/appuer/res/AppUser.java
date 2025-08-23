package _bbu.lawfirmapi.models.DTO.appuer.res;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    private Integer appUserId;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private String description;
}
