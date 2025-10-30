package _bbu.lawfirmapi.services.client;

import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.Entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    ClientResponse createNewClient(ClientRequest clientRequest);
    ClientResponse modifiedClientById(ClientRequest clientRequest , Long clientId);
    Void removeClientById(Long clientId);
}
