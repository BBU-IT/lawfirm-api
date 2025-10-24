package _bbu.lawfirmapi.services.cases;

import _bbu.lawfirmapi.models.DTO.cases.request.CaseRequest;
import _bbu.lawfirmapi.models.DTO.cases.response.CaseResponse;
import _bbu.lawfirmapi.models.Entity.Case;
import java.util.List;

public interface CaseService {

    List<Case> getCaseList();

    CaseResponse createNewCase(CaseRequest caseRequest);
}
