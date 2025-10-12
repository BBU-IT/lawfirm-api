package _bbu.lawfirmapi.services.appointment;

import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointment();
    AppointmentResponse createNewAppointment(@RequestBody AppointmentRequest appointmentRequest);
}
