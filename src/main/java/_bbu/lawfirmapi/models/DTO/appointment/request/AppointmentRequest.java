package _bbu.lawfirmapi.models.DTO.appointment.request;

import _bbu.lawfirmapi.models.Entity.*;
import _bbu.lawfirmapi.models.Enumerations.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppointmentRequest {

    private Client clients;
    private AppUser appUser;
    private LocalDateTime appointmentDate;
    private String location;
    private String purpose;
    private AppointmentStatus appointmentStatus;
    private Case cases;


    public Appointment toEntity(){
        return new Appointment(null , this.clients , this.cases , this.appUser , this.appointmentDate , this.location , this.purpose , this.appointmentStatus);
    }
}
