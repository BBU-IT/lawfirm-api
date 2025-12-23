package _bbu.lawfirmapi.services.client.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.client.request.ClientRequest;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.ClientRepository;
import _bbu.lawfirmapi.services.client.ClientService;
import _bbu.lawfirmapi.utils.MethodHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private  final ClientRepository clientRepository;
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private MethodHelper checkOutOfPage;
    @Value("${spring.mail.username}")
    private String adminEmail;
    public AppUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("DD" + authentication);
        if(authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            return null;
        }
        return (AppUser) authentication.getPrincipal();
    }

    public Page<Client> getAllClients(Pageable pageable , Integer requestedPage) {
//        AppUser currentUser = getCurrentUser();
//
//        System.out.println("Client " + currentUser);
//        if (currentUser == null) {
//            throw new RuntimeException("User not authenticated");
//        }
//
//        String roleName = currentUser.getRole().getRoleName();
//
//        // Check authorization
//        if (roleName.equals("ROLE_ANONYMOUS")) {
//            throw new RuntimeException("You don't have access to this endpoint.");
//        }
        Page<Client> clients = clientRepository.findAll(pageable); // Get ALL clients

//        checkOutOfPage.isInvalidPage(clients.getTotalPages() , requestedPage);

        if (clients.isEmpty()) {
            throw new NotFoundException("No client list found");
        }

        return clients;
    }

    @Override
    public Client getClientById(Long clientId){
        return clientRepository.findById(clientId).
                orElseThrow(() -> new NotFoundException("Client id " + clientId + " not found."));
    }
    public ClientResponse createNewClient(ClientRequest request) throws MessagingException {
        // prepare mail to user
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("subject", "CGLaw Firm Notification");
        context.setVariable("user", request.getClientName()); // better to use user's full name
        context.setVariable("caseId", request.getEmail()); // case identifier
        context.setVariable("caseLink", "https://your-lawfirm.com/cases/" + request.getComplaint()); // link to case status

        context.setVariable("clientImage",  request.getClientImage()); // link to case status

// Process the template
        String htmlContent = templateEngine.process("notification-template", context);

        mimeMessageHelper.setSubject("Update on Your Law Case");
        mimeMessageHelper.setTo(request.getEmail());
        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        Client client = request.toEntity();
        client.setClientName(request.getClientName());
        client.setEmail(request.getEmail());
        client.setStatus(request.getStatus());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setAddress(request.getAddress());
        client.setComplaint(request.getComplaint());
        client.setClientImage(request.getClientImage());
        Client savedClient = clientRepository.save(client);
        return savedClient.toResponse();
    }

    @Override
    public ClientResponse modifiedClientById(ClientRequest clientRequest , Long clientId){
        Client previousClient = clientRepository.findById(clientId).
                orElseThrow(() -> new NotFoundException("Client not found"));
        previousClient = clientRequest.toEntity();
        previousClient.setClientName(clientRequest.getClientName());
        previousClient.setEmail(clientRequest.getEmail());
        previousClient.setStatus(clientRequest.getStatus());
        previousClient.setPhoneNumber(clientRequest.getPhoneNumber());
        previousClient.setAddress(clientRequest.getAddress());
        previousClient.setComplaint(clientRequest.getComplaint());
        previousClient.setClientImage(clientRequest.getClientImage());
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
