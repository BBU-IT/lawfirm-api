package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.RequestService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestServiceRepository extends JpaRepository<RequestService , Long> {
}
