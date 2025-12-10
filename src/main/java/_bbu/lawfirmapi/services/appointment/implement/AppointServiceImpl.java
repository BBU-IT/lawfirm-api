package _bbu.lawfirmapi.services.appointment.implement;


import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.AppointmentRepository;
import _bbu.lawfirmapi.repositories.CaseRepository;
import _bbu.lawfirmapi.repositories.ClientRepository;
import _bbu.lawfirmapi.services.appointment.AppointmentService;
import _bbu.lawfirmapi.utils.MethodHelper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final ClientRepository clientRepo;
    private final CaseRepository caseRepo;
    private final AppUserRepository appUserRepo;
    private final MethodHelper checkOutOfPage;

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId).orElseThrow(
                () -> new NotFoundException("Sorry! Appointment with id " + appointmentId + " not found.")
        );
    }
    @Override
    public Page<Appointment> getAllAppointments(Pageable pageable , Integer requestPage) {
        Page<Appointment> appointmentsList = appointmentRepo.findAll(pageable);
        checkOutOfPage.isInvalidPage(appointmentsList.getTotalPages() , requestPage);
        if (appointmentsList.isEmpty()){
            throw new NotFoundException("No appointment list here.");
        }
        return appointmentsList;
    }
    @Override
    public AppointmentResponse createNewAppointment(AppointmentRequest appointmentRequest) {

        Appointment newAppointment = appointmentRequest.toEntity();

        Case myCase = caseRepo.findById(appointmentRequest.getCaseId()).orElseThrow(
                () -> new NotFoundException("Case for this appointment not found.")
        );

        newAppointment.setACase(myCase);
        newAppointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        newAppointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        newAppointment.setMeetingType(appointmentRequest.getMeetingType());
        newAppointment.setLocation(appointmentRequest.getLocation());
        newAppointment.setPurpose(appointmentRequest.getPurpose());
        newAppointment.setStatus(appointmentRequest.getStatus());

        return appointmentRepo.save(newAppointment).toResponse();
    }

    @Override
    public AppointmentResponse modifiedAppointmentById(Long appointmentId, AppointmentRequest appointmentRequest) {
       Appointment currentAppointment = appointmentRepo.findById(appointmentId)
               .orElseThrow(() -> new NotFoundException("This appointment not found ."));
        Case newCase = caseRepo.findById(appointmentRequest.getCaseId()).orElseThrow(
                () -> new NotFoundException("Case for this appointment not found.")
        );
        currentAppointment.setACase(newCase);
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
