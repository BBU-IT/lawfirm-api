package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.expertise.request.ExpertiseRequest;
import _bbu.lawfirmapi.models.DTO.expertise.response.ExpertiseResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Expertise;
import _bbu.lawfirmapi.services.expertise.ExpertiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expertises")
public class ExpertiseController extends BaseResponse {
    private final ExpertiseService expertiseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Expertise>>> getExpertiseList(){
        return responseEntity(true ,
                "Getting expertise list successfully",
                HttpStatus.OK,
        expertiseService.fetchAllExpertise());
    }
    @PostMapping
    public ResponseEntity<ApiResponse<ExpertiseResponse>> createExpertise(@RequestBody ExpertiseRequest expertiseRequest){

        return responseEntity(true ,
                "Create new expertise name " + expertiseRequest.getExpertName() +  " successfully" ,
                HttpStatus.CREATED,
                expertiseService.createNewExpertise(expertiseRequest));
    }
    @PutMapping("/{expertiseId}")
    public ResponseEntity<ApiResponse<ExpertiseResponse>> updateExistExpertise(
            @RequestBody ExpertiseRequest expertiseRequest,
            @PathVariable Integer expertiseId ){
        return responseEntity(true,
                "update expertise name " +  expertiseService.fetchExpertiseById(expertiseId).getExpertName()  + " to " + expertiseRequest.getExpertName()  +  " successfully",
                HttpStatus.ACCEPTED ,
                expertiseService.updateExistExpertiseById(expertiseRequest , expertiseId));
    }
    @DeleteMapping("/{expertiseId}")
    public ResponseEntity<ApiResponse<Void>> deleteExistingExpertise(@PathVariable Integer expertiseId){
        return responseEntity(true ,
                "Delete expertise id " + expertiseId + " name " + expertiseService.fetchExpertiseById(expertiseId).getExpertName() + " successfully",
                HttpStatus.ACCEPTED,
                expertiseService.removeExistExpertiseById(expertiseId));

    }
}
