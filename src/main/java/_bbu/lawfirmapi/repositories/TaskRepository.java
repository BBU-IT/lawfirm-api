package _bbu.lawfirmapi.repositories;


import _bbu.lawfirmapi.models.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    boolean existsByLawyer_AppUserIdAndLegalCase_CaseId(Long lawyerAppUserId, Long legalCaseCaseId);

    Page<Task> findTaskByLawyerEmail(Pageable pageable, String lawyer_email);
}
