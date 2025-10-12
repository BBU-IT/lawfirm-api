package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository  extends JpaRepository<Appointment , Long> {
}
