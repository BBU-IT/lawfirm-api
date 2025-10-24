package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.court.request.CourtRequest;
import _bbu.lawfirmapi.models.DTO.court.response.CourtResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Court;
import _bbu.lawfirmapi.services.court.CourtService;
import _bbu.lawfirmapi.utils.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courts")
public class CourtController extends BaseResponse {

    public final CourtService courtService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Court>>> getAllCourts(){
        return responseEntity(true , "Get all court successfully" , HttpStatus.OK , courtService.getCourtList());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourtResponse>> createCourt(@RequestBody CourtRequest courtRequest){
        return responseEntity(true , "Create new court successfully" , HttpStatus.CREATED , courtService.createNewCourt(courtRequest));
    }




}
