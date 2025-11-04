package _bbu.lawfirmapi.services.client.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.ClientRepository;
import _bbu.lawfirmapi.services.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private  final ClientRepository clientRepository;
    private final AppUserRepository appUserRepository;

    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            return null;
        }
        return (AppUser) authentication.getPrincipal();
    }

    public Page<Client> getAllClients(Pageable pageable) {
        AppUser currentUser = getCurrentUser();

        if (currentUser == null) {
            throw new RuntimeException("User not authenticated");
        }

        String roleName = currentUser.getRole().getRoleName();

        // Check authorization
        if (roleName.equals("ROLE_USER")) {
            throw new RuntimeException("You don't have access to this endpoint.");
        }

        Page<Client> clients;

        // Admins can see ALL clients
        if (roleName.equals("ROLE_ADMIN")) {
            clients = clientRepository.findAll(pageable); // Get ALL clients
        }
        // Other roles (e.g., ROLE_LAWYER, ROLE_STAFF) see only their own clients
        else {
            clients = clientRepository.findClientByAppUserId(currentUser.getAppUserId() , pageable);
        }

        if (clients.isEmpty()) {
            throw new NotFoundException("No client list found");
        }

        return clients;
    }

    public ClientResponse createNewClient(ClientRequest request) {
        AppUser appUser = appUserRepository.findById(request.getAppUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Client client = request.toEntity(appUser);
        client.setClientName(request.getClientName());
        client.setEmail(request.getEmail());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(request.getAddress());
        client.setMessage(request.getMessage());
        client.setAppUser(appUser);
        Client savedClient = clientRepository.save(client);
        return savedClient.toResponse();
    }

    @Override
    public ClientResponse modifiedClientById(ClientRequest clientRequest , Long clientId){
        AppUser appUser = appUserRepository.findById(clientRequest.getAppUserId())
                .orElseThrow(() -> new NotFoundException("Sorry, User not found"));
        Client previousClient = clientRepository.findById(clientId).
                orElseThrow(() -> new NotFoundException("Client not found"));
        previousClient = clientRequest.toEntity(appUser);
        previousClient.setClientName(clientRequest.getClientName());
        previousClient.setEmail(clientRequest.getEmail());
        previousClient.setPhoneNumber(clientRequest.getPhoneNumber());
        previousClient.setAddress(clientRequest.getAddress());
        previousClient.setMessage(clientRequest.getMessage());
        previousClient.setAppUser(appUser);
//        Client updatedClient = clientRepository.save(previousClient);
        return clientRepository.save(previousClient).toResponse();
    }
    @Override
    public Void removeClientById(Long clientId){
        if(clientRepository.existsById(clientId)){
            clientRepository.deleteById(clientId);
        }
        return null;
    }
}
