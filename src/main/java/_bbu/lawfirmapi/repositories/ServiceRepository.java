package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service , Long> {
}
