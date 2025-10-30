package _bbu.lawfirmapi.models.DTO.client.request;

import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientRequest {

    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;
    private String message;
    private Long appUserId;
    public Client toEntity(AppUser appUser){
        return new Client(null , this.clientName , this.email , this.phoneNumber , this.address , this.message , appUser);
    }

}
