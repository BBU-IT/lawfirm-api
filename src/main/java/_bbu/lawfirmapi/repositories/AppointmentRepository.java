package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppointmentRepository  extends JpaRepository<Appointment , Long> {

    @Query("SELECT a FROM Appointment  a JOIN FETCH a.task WHERE a.task.lawyer.email = :email")
    Page<Appointment> findAllWithAppUser(Pageable pageable , @Param("email") String email);

    @Query("SELECT a FROM Appointment  a JOIN FETCH a.task WHERE a.appointmentId = :appointmentId AND a.task.lawyer.email = :email")
    Optional<Appointment> findAppointmentByAppointmentId(@Param("appointmentId") Long appointmentId , @Param("email") String email);

}
