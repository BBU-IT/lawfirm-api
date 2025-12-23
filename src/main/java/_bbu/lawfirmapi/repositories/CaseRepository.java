package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    @Query("SELECT DISTINCT c FROM Case c JOIN FETCH c.client  JOIN FETCH c.court ")
    List<Case> findAllWithCases();
}
