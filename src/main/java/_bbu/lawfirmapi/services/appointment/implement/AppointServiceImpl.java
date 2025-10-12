package _bbu.lawfirmapi.services.appointment.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.repositories.AppointmentRepository;
import _bbu.lawfirmapi.services.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointment(){

       if (appointmentRepository.findAll().isEmpty()){
           throw  new NotFoundException("Appointment not found right now.");
       }
       return appointmentRepository.findAll();
    }
    @Override
    public AppointmentResponse createNewAppointment(AppointmentRequest appointmentRequest){
        Appointment newAppointment = appointmentRequest.toEntity();
        newAppointment.setAppUser(appointmentRequest.getAppUser());
        newAppointment.setACase(appointmentRequest.getCases());
        newAppointment.setClient(appointmentRequest.getClients());
        newAppointment.setLocation(appointmentRequest.getLocation());
        newAppointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        newAppointment.setPurpose(appointmentRequest.getPurpose());
        newAppointment.setStatus(appointmentRequest.getAppointmentStatus());
        return appointmentRepository.save(newAppointment).toResponse();
    }
}
