package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client , Long> {
}
