package _bbu.lawfirmapi.services.appointment.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentFilterRequest;
import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.*;
import _bbu.lawfirmapi.models.specification.AppointmentSpecification;
import _bbu.lawfirmapi.repositories.*;
import _bbu.lawfirmapi.services.appointment.AppointmentService;
import _bbu.lawfirmapi.utils.MethodHelper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AppointServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final ClientRepository clientRepo;
    private final TaskRepository taskRepo;
    private final AppUserRepository appUserRepo;
    private final MethodHelper methodHelper;
    private final AppointmentSpecification appointmentSpecification;

    public  Authentication getCurrentLawyerEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Unauthenticated");
        }


        return auth;
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {

        Appointment appointment;

        if (methodHelper.isLawyer(getCurrentLawyerEntity()) ) {
            appointment = appointmentRepo.findAppointmentByAppointmentId(id , getCurrentLawyerEntity().getName())
                    .orElseThrow(() -> new NotFoundException("Appointment not found"));
        } else {
            appointment = appointmentRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Appointment not found"));
        }

        return appointment.toResponse();
    }

    @Override
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable , Integer requestPage) {

        Page<Appointment> appointmentsList ;
        if(methodHelper.isAdmin(getCurrentLawyerEntity())){
            appointmentsList = appointmentRepo.findAll(pageable);
        }
        else if(methodHelper.isLawyer(getCurrentLawyerEntity())){
            appointmentsList = appointmentRepo.findAllWithAppUser(pageable , getCurrentLawyerEntity().getName());
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
        methodHelper.isInvalidPage(appointmentsList.getTotalPages() , requestPage);
        if (appointmentsList.isEmpty()){
            throw new NotFoundException("No appointment list here.");
        }
        return appointmentsList.map(Appointment::toResponse);
    }

    @Override
    public Page<AppointmentResponse> searchAllAppointmentBy(
            Pageable pageable,
            Integer requestPage,
            String keyword
    ) {

        boolean isAdmin = methodHelper.isAdmin(getCurrentLawyerEntity());
        boolean isLawyer = methodHelper.isLawyer(getCurrentLawyerEntity());

        if (!isAdmin && !isLawyer) {
            throw new AccessDeniedException("Access denied");
        }

//        Long lawyerId = isAdmin ? null : ge;

        Specification<Appointment> spec =
                AppointmentSpecification.search(
                        keyword
//                        lawyerId,
//                        isAdmin
                );

        Page<Appointment> appointmentsList =
                appointmentRepo.findAll(spec, pageable);

        methodHelper.isInvalidPage(
                appointmentsList.getTotalPages(),
                requestPage
        );

        if (appointmentsList.isEmpty()) {
            throw new NotFoundException("No appointment list here.");
        }

        return appointmentsList.map(Appointment::toResponse);
    }

    @Override
    public Page<AppointmentResponse> getFilterAppointment(AppointmentFilterRequest appointmentFilterRequest , Pageable pageable , Integer requestedPage){

        Page<AppointmentResponse> filterdAppointment =  appointmentRepo.findAll(AppointmentSpecification.withFilters(appointmentFilterRequest) , pageable)
                .map(Appointment::toResponse);
        methodHelper.isInvalidPage(filterdAppointment.getTotalPages() , requestedPage);
        if (filterdAppointment.isEmpty()){
            throw new NotFoundException("No appointment list here.");
        }

        return filterdAppointment;

    }

    @Override
    public AppointmentResponse createNewAppointment(AppointmentRequest appointmentRequest) {

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Appointment newAppointment = appointmentRequest.toEntity();
//        if(!methodHelper.isLawyer(auth)){
//            throw new AccessDeniedException("Only lawyers can create the new appointment");
//        }

        Task assignedTask = taskRepo.findById(appointmentRequest.getTaskId())
                        .orElseThrow(
                                () -> new NotFoundException("task not found.")
                        );
        newAppointment.setTask(assignedTask);
        newAppointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        newAppointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        newAppointment.setMeetingType(appointmentRequest.getMeetingType());
        newAppointment.setLocation(appointmentRequest.getLocation());
        newAppointment.setPurpose(appointmentRequest.getPurpose());
        newAppointment.setStatus(appointmentRequest.getStatus());
        newAppointment.setCreatedAt(LocalDateTime.now());

        return appointmentRepo.save(newAppointment).toResponse();
    }

    @Override
    public AppointmentResponse modifiedAppointmentById(Long appointmentId, AppointmentRequest appointmentRequest) {
       Appointment currentAppointment = appointmentRepo.findById(appointmentId)
               .orElseThrow(() -> new NotFoundException("This appointment not found ."));
        Task assignedTask = taskRepo.findById(appointmentRequest.getTaskId())
                .orElseThrow(
                        () -> new NotFoundException("task id not found.")
                );
        currentAppointment.setTask(assignedTask);
        currentAppointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        currentAppointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        currentAppointment.setMeetingType(appointmentRequest.getMeetingType());
        currentAppointment.setLocation(appointmentRequest.getLocation());
        currentAppointment.setPurpose(appointmentRequest.getPurpose());
        currentAppointment.setStatus(appointmentRequest.getStatus());

        Appointment updatedAppointment = appointmentRepo.save(currentAppointment);
        return updatedAppointment.toResponse();
    }

    @Override
    public Void removeAppointmentById(Long appointmentId) {
        if (appointmentRepo.findById(appointmentId).isEmpty()){
            throw new NotFoundException("This appointment not found .");
        }
        appointmentRepo.deleteById(appointmentId);
        return null;
    }
}
