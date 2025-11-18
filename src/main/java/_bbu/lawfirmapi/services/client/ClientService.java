package _bbu.lawfirmapi.services.client;

import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.Entity.Client;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    Page<Client> getAllClients(Pageable pageable , Integer requestedPage);
    Client getClientById(Long clientId);
    ClientResponse createNewClient(ClientRequest clientRequest) throws MessagingException;
    ClientResponse modifiedClientById(ClientRequest clientRequest , Long clientId);
    Void removeClientById(Long clientId);
}
