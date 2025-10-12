package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Enumerations.AppointmentStatus;
import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "client_id" , referencedColumnName = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "case_id" , referencedColumnName = "case_id")
    private Case aCase;

    @ManyToOne
    @JoinColumn(name = "schedule_by" , referencedColumnName = "appuser_id")
    private AppUser appUser;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @Column(name = "location")

    private String location;

    @Column(name = "purpose")
    private String purpose;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;


    public AppointmentResponse toResponse(){
        return AppointmentResponse.builder()
                .id(this.appointmentId)
                .appUser(this.appUser)
                .cases(this.aCase)
                .clients(this.client)
                .appointmentDate(this.appointmentDate)
                .appointmentStatus(this.status)
                .build();
    }
}
