package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository< Document , Long> , JpaSpecificationExecutor<Document> {
}
