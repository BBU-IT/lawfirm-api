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

    @GetMapping("/{courtId}")
    public ResponseEntity<ApiResponse<Court>> getCourtById(@PathVariable Long courtId){
        return responseEntity(true ,
                "Get court with id " + courtId + " successfully" ,
                HttpStatus.OK ,
                courtService.getCourtById(courtId));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Court>>> getAllCourts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "appointmentId") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ){
        return responseEntity(true ,
                "Get all court successfully" ,
                HttpStatus.OK ,
                courtService.getCourtList());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourtResponse>> createCourt(@RequestBody CourtRequest courtRequest){
        return responseEntity(true ,
                "Create new court successfully" ,
                HttpStatus.CREATED ,
                courtService.createNewCourt(courtRequest));
    }
    @PutMapping("/{courtId}")
    public ResponseEntity<ApiResponse<CourtResponse>> updateNewCourt(@RequestBody CourtRequest courtRequest , @PathVariable Long courtId){
        return responseEntity(true ,
                "Update court with name " + courtService.getCourtById(courtId).getCourtName() +  " to " + courtRequest.getCourtName(),
                HttpStatus.ACCEPTED,
                courtService.modifiedCourtById(courtRequest , courtId));
    }
    @DeleteMapping("/{courtId}")
    public ResponseEntity<ApiResponse<Void>> deleteExistCourtById(@PathVariable  Long courtId){
        return responseEntity(true ,
                "Delete court with id " + courtId + " successfully",
                HttpStatus.OK,
                courtService.removeCourtById(courtId));
    }




}
