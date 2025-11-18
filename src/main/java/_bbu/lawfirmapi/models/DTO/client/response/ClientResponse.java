package _bbu.lawfirmapi.models.DTO.client.response;

import _bbu.lawfirmapi.models.Entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientResponse {

    private Long  clientId ;
    private String clientName ;
    private String email ;
    private String phoneNumber;
    private String address;
    private String complaint ;
    private String clientImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
