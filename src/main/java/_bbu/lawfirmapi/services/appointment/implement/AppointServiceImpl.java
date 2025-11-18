package _bbu.lawfirmapi.services.appointment.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.repositories.AppointmentRepository;
import _bbu.lawfirmapi.services.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Page<Appointment> getAllAppointment(Pageable pageable){
        return Optional.of(appointmentRepository.findAll(pageable))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException("Appointment list not found"));
    }
    @Override
    public AppointmentResponse createNewAppointment(AppointmentRequest appointmentRequest){
        Appointment newAppointment = appointmentRequest.toEntity();
        newAppointment.setACase(appointmentRequest.getCases());
        newAppointment.setLocation(appointmentRequest.getLocation());
        newAppointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        newAppointment.setPurpose(appointmentRequest.getPurpose());
        newAppointment.setStatus(appointmentRequest.getAppointmentStatus());
        return appointmentRepository.save(newAppointment).toResponse();
    }
}
