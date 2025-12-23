package _bbu.lawfirmapi.services.cases;

import _bbu.lawfirmapi.models.DTO.cases.request.CaseRequest;
import _bbu.lawfirmapi.models.DTO.cases.response.CaseResponse;
import _bbu.lawfirmapi.models.Entity.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CaseService {

    Page<Case> getCaseList(Pageable pageable , Integer requestedPage);

    CaseResponse createNewCase(CaseRequest caseRequest);

    CaseResponse modifiedCaseById(Long caseId , CaseRequest caseRequest);

    Void removeCaseById(Long caseId);


}
