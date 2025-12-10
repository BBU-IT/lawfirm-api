package _bbu.lawfirmapi.services.appointment;

import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AppointmentService {

    Page<Appointment> getAllAppointments(Pageable pageable , Integer requestPage);
    Appointment getAppointmentById(Long appointmentId);
    AppointmentResponse createNewAppointment( AppointmentRequest appointmentRequest);
    AppointmentResponse modifiedAppointmentById(Long appointmentId , AppointmentRequest appointmentRequest);
    Void removeAppointmentById(Long appointmentId);
}
