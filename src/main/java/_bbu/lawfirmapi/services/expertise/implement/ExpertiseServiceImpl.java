package _bbu.lawfirmapi.services.expertise.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.expertise.request.ExpertiseRequest;
import _bbu.lawfirmapi.models.DTO.expertise.response.ExpertiseResponse;
import _bbu.lawfirmapi.models.Entity.Expertise;
import _bbu.lawfirmapi.repositories.ExpertiseRepository;
import _bbu.lawfirmapi.services.expertise.ExpertiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpertiseServiceImpl implements ExpertiseService {

    private final ExpertiseRepository expertiseRepo;

    @Override
    public List<Expertise> fetchAllExpertise() {
        if(expertiseRepo.findAll().isEmpty()) {
            throw new NotFoundException("No expertise list found.");
        }

        return expertiseRepo.findAll();
    }
    @Override
    public Expertise fetchExpertiseById(Integer expertiseId) {

        return expertiseRepo.findById(expertiseId).orElseThrow(() -> new NotFoundException("Expertise with id " + expertiseId + " not found."));
    }

    @Override
    public ExpertiseResponse createNewExpertise(ExpertiseRequest expertiseRequest) {
        Expertise newExpertise  = expertiseRequest.toEntity();
        newExpertise.setExpertName(expertiseRequest.getExpertName());
        newExpertise.setCreatedAt(LocalDateTime.now());
        newExpertise.setUpdatedAt(LocalDateTime.now());
        return expertiseRepo.save(newExpertise).toResponse();
    }

    @Override
    public ExpertiseResponse updateExistExpertiseById(ExpertiseRequest expertiseRequest, Integer expertiseId) {
        Expertise previousExpertise = expertiseRepo.findById(expertiseId).orElseThrow(() -> new NotFoundException("Cannot update expertise name " +
                expertiseRequest.getExpertName() + "Because expertise id" +  expertiseId + " not found."));
        System.out.println("My previous :" + previousExpertise);
        previousExpertise.setExpertName(expertiseRequest.getExpertName());
        previousExpertise.setUpdatedAt(LocalDateTime.now());
        Expertise updatedExpertise = expertiseRepo.save(previousExpertise);
        System.out.println("My update : "+ updatedExpertise);
        return updatedExpertise.toResponse();
    }

    @Override
    public Void removeExistExpertiseById(Integer expertiseId) {
        expertiseRepo.findById(expertiseId).orElseThrow(() -> new NotFoundException("Expertise with id " + expertiseId + " not found."));
        expertiseRepo.deleteById(expertiseId);
        return null;
    }
}
