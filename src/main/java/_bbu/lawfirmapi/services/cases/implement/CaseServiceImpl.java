package _bbu.lawfirmapi.services.cases.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.cases.request.CaseRequest;
import _bbu.lawfirmapi.models.DTO.cases.response.CaseResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.models.Entity.Court;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.CaseRepository;
import _bbu.lawfirmapi.repositories.ClientRepository;
import _bbu.lawfirmapi.repositories.CourtRepository;
import _bbu.lawfirmapi.services.cases.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService  {


    private final CaseRepository caseRepository;
    private final ClientRepository clientRepository;
    private final CourtRepository courtRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Case> getCaseList() {
        List<Case> n = caseRepository.findAll().stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(n);
        return caseRepository.findAll().stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public CaseResponse createNewCase(CaseRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Court court = courtRepository.findById(request.getCourtId())
                .orElseThrow(() -> new RuntimeException("Court not found"));
        AppUser appUser = appUserRepository.findById(request.getAppUserId())
                .orElseThrow(() -> new RuntimeException("AppUser not found"));


        Case newCase = request.toEntity(client , court ,appUser);

        newCase.setTitle(request.getTitle());
        newCase.setDescription(request.getDescription());
        newCase.setStatus(request.getStatus());
        newCase.setStartDate(request.getStatedDate());
        newCase.setEndDate(request.getEndedDate());

        System.out.println("New case " + newCase);

        return caseRepository.save(newCase).toResponse();
    }


}
