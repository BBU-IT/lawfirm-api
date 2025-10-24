package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.cases.request.CaseRequest;
import _bbu.lawfirmapi.models.DTO.cases.response.CaseResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.services.cases.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cases")
public class CaseController extends BaseResponse {

    private final CaseService caseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Case>>> getAllCase(){

        return responseEntity(true,
                "Get all cases successfully",
                HttpStatus.OK,
                caseService.getCaseList());
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CaseResponse>> createNewCase(@RequestBody CaseRequest caseRequest){
        System.out.println(caseService.createNewCase(caseRequest));
        return responseEntity(true ,
                "Create new case successfully",
                HttpStatus.CREATED,
                caseService.createNewCase(caseRequest));
    }

}
