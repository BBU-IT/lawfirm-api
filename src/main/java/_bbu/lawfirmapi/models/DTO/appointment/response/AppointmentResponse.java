package _bbu.lawfirmapi.models.DTO.appointment.response;

import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.models.Entity.Court;
import _bbu.lawfirmapi.models.Enumerations.AppointmentStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class AppointmentResponse {

    private Long id;
    private LocalDateTime appointmentDate;
    private String location;
    private String purpose;
    private AppointmentStatus appointmentStatus;
    private Case cases;

}
