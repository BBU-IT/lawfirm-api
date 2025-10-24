package _bbu.lawfirmapi.models.DTO.client.request;

import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientRequest {

    private String clientName;
    private String email;
    private String phoneNumber;
    private String address;
    private String message;

    public Client toEntity(){
        return new Client(null , this.clientName , this.email , this.phoneNumber , this.address , this.message );
    }

}
