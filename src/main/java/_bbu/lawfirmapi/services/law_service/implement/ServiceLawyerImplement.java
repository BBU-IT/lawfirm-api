package _bbu.lawfirmapi.services.law_service.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.service.request.ServiceRequest;
import _bbu.lawfirmapi.models.DTO.service.response.ServiceResponse;
import _bbu.lawfirmapi.models.Entity.Expertise;
import _bbu.lawfirmapi.repositories.ExpertiseRepository;
import _bbu.lawfirmapi.repositories.ServiceRepository;
import _bbu.lawfirmapi.services.law_service.ServiceLawyerService;
import _bbu.lawfirmapi.utils.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServiceLawyerImplement implements ServiceLawyerService {

    private final ServiceRepository serviceRepo;
    private final MethodHelper checkOutOfPage;
    private final ExpertiseRepository expertiseRepo;

    /* this "_bbu.lawfirmapi.models.Entity.Service" is the Service Entity type
     because it's confuse with Service annotation  */
    @Override

    public _bbu.lawfirmapi.models.Entity.Service getLawyerServiceById(Long serviceId) {
        return serviceRepo.findById(serviceId).orElseThrow(() -> new NotFoundException("Service with id " + serviceId +  " not found"));
    }

    @Override
    public Page<_bbu.lawfirmapi.models.Entity.Service> getAllLawyerService(Pageable pageable, Integer requestedPage) {
        Page<_bbu.lawfirmapi.models.Entity.Service> serviceList = serviceRepo.findAll(pageable);
        checkOutOfPage.isInvalidPage(serviceList.getTotalPages(), requestedPage);
        if (serviceList.isEmpty()) {
            throw new NotFoundException("No service list found");
        }
        return serviceList;
    }

    @Override
    public ServiceResponse createNewLawyerService(ServiceRequest serviceRequest) {
        _bbu.lawfirmapi.models.Entity.Service newService = serviceRequest.toEntity();
        Expertise expertise = expertiseRepo.findById(serviceRequest.getExpertiseId()).orElseThrow(
                () -> new NotFoundException("Expertise not found for this new service.")
        );
        newService.setServiceName(serviceRequest.getServiceName());
        newService.setDescription(serviceRequest.getDescription());
        newService.setBasePrice(serviceRequest.getBasePrice());
        newService.setExpertise(expertise);
        newService.setBasePrice(serviceRequest.getBasePrice());
        newService.setCreatedAt(LocalDateTime.now());
        newService.setUpdatedAt(LocalDateTime.now());
        return serviceRepo.save(newService).toResponse();
    }

    @Override
    public ServiceResponse modifiedExistingLawyerServiceById(Long serviceId, ServiceRequest serviceRequest) {
        _bbu.lawfirmapi.models.Entity.Service currentService = serviceRepo.findById(serviceId).
                orElseThrow(() -> new NotFoundException("The service that you want to update not found."));
        Expertise expertise = expertiseRepo.findById(serviceRequest.getExpertiseId()).orElseThrow(
                () -> new NotFoundException("Expertise not found for this new service.")
        );
        currentService.setServiceName(serviceRequest.getServiceName());
        currentService.setDescription(serviceRequest.getDescription());
        currentService.setExpertise(expertise);
        currentService.setBasePrice(serviceRequest.getBasePrice());
        currentService.setUpdatedAt(LocalDateTime.now());
        return serviceRepo.save(currentService).toResponse();
    }
    @Override
    public Void removeLawyerServiceById(Long serviceId) {
        if(serviceRepo.existsById(serviceId)){
            serviceRepo.deleteById(serviceId);
        }
        return null;
    }
}
