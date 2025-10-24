package _bbu.lawfirmapi.models.DTO.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ClientResponse {

    private Long  clientId ;

    private String clientName ;

    private String email ;

    private String phoneNumber;

    private String address;

    private String message ;

}
