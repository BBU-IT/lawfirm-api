package _bbu.lawfirmapi.models.DTO.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthResponse {
    private String token;
    private String expiredTime;
}
