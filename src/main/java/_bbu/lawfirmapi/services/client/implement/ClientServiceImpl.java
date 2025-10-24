package _bbu.lawfirmapi.services.client.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.repositories.ClientRepository;
import _bbu.lawfirmapi.services.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private  final ClientRepository clientRepository;

    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            return null;
        }
        return (AppUser) authentication.getPrincipal();
    }

    @Override
    public List<Client> getAllClient() {
        System.out.println("My current user " + getCurrentUser());
        return Optional.of(clientRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException("No list customer found"));
    }

    @Override
    public ClientResponse createNewClient(ClientRequest clientRequest) {
        Client newClient = clientRequest.toEntity();
        newClient.setClientName(clientRequest.getClientName());
        newClient.setEmail(clientRequest.getEmail());
        newClient.setAddress(clientRequest.getAddress());
        newClient.setMessage(clientRequest.getMessage());
        newClient.setPhoneNumber(clientRequest.getPhoneNumber());
        newClient.setCreatedAt(clientRequest.toEntity().getCreatedAt());
        return clientRepository.save(newClient).toResponse();
    }
}
