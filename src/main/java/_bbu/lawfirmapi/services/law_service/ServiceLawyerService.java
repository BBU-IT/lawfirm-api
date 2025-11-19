package _bbu.lawfirmapi.services.law_service;

import _bbu.lawfirmapi.models.DTO.service.request.ServiceRequest;
import _bbu.lawfirmapi.models.DTO.service.response.ServiceResponse;
import _bbu.lawfirmapi.models.Entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceLawyerService {

    Service getLawyerServiceById(Long serviceId);
    Page<Service> getAllLawyerService(Pageable pageable , Integer requestedPage);
    ServiceResponse createNewLawyerService(ServiceRequest serviceRequest);
    ServiceResponse modifiedExistingLawyerServiceById(Long serviceId, ServiceRequest serviceRequest);
    Void removeLawyerServiceById(Long serviceId);

}
