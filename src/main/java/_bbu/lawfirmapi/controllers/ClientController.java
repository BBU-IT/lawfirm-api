package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.services.client.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
@SecurityRequirement(name = "bearerAuth")

public class ClientController extends BaseResponse {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<ApiResponse<Slice<Client>>> getAllClient(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "clientId") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending

    ){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Client> clients = clientService.getAllClients(pageable , page);
        return responseEntity(true ,
                "Get all client list",
                HttpStatus.OK,
                clients);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResponse<Client>> retrieveClientById(@PathVariable Long clientId){
        return responseEntity(true ,
                "get client id " +  clientId +  " with name " +  clientService.getClientById(clientId),
                HttpStatus.ACCEPTED,
                clientService.getClientById(clientId));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponse>> createNewClient(@RequestBody ClientRequest clientRequest) throws MessagingException {
        return responseEntity(true ,
                "Create new client successfully",
                HttpStatus.CREATED,
                clientService.createNewClient(clientRequest));
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ApiResponse<ClientResponse>> modifiedClientById(
            @RequestBody ClientRequest clientRequest ,
            @PathVariable @Valid @Positive Long clientId){
        return responseEntity(true,
                "Update client id " + clientId + " successfully",
                HttpStatus.ACCEPTED,
                clientService.modifiedClientById(clientRequest , clientId));
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> removeClientById(Long clientId){
        return responseEntity(true ,
                "Delete client id " + clientId + " successfully",
                HttpStatus.ACCEPTED,
                clientService.removeClientById(clientId)
                );
    }
}
