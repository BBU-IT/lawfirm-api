package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request , Long> {
}
