package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.services.client.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController extends BaseResponse {

    private final ClientService clientService;
    @SecurityRequirement(name = "bearerAuth")

    @GetMapping
    public ResponseEntity<ApiResponse<List<Client>>> getAllClient(){
        return responseEntity(true ,
                "Get all client list",
                HttpStatus.OK,
                clientService.getAllClient());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponse>> createNewClient(@RequestBody ClientRequest clientRequest){
        return responseEntity(true ,
                "Create new client successfully",
                HttpStatus.CREATED,
                clientService.createNewClient(clientRequest));
    }
}
