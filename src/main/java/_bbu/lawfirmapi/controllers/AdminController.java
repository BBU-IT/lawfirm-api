package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.services.admin.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.internal.CoreLogging.logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController extends BaseResponse {
    private final AdminService adminService;

    @GetMapping("/lawyers")
    public ResponseEntity<ApiResponse<Page<AppUserResponse>>> getAllUser(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "appUserId") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<AppUserResponse> lawyers = adminService.getAllUser(pageable , page);
        return responseEntity(true ,
                "Get all lawyers successfully." ,
                HttpStatus.OK ,
                lawyers);
    }
    @GetMapping("/lawyers/no-pagination")
    public ResponseEntity<ApiResponse<List<AppUserResponse>>> fetchLawyerNoPagination(){
        return responseEntity(true ,
                "Get all lawyers successfully." ,
                HttpStatus.OK ,
                adminService.getAllLawyerListNoPagination());
    }
    @GetMapping("/lawyers/{lawyerId}")
    public ResponseEntity<ApiResponse<AppUserResponse>> fetchLawyerById(@PathVariable Long lawyerId){
        return responseEntity(true ,
                STR."Get lawyer with id \{lawyerId} successfully.",
                HttpStatus.ACCEPTED,
                adminService.getLawyerById(lawyerId)
                );
    }

    @PutMapping("/lawyers/{lawyerId}")
    public ResponseEntity<ApiResponse<AppUserResponse>> updateExistLawyerById(@RequestBody AppUserRequest appUserRequest ,
                                                                              @PathVariable Long lawyerId ){
        logger(appUserRequest.getClass());
        return responseEntity(true,
                STR."Update lawyer id \{lawyerId} successfully",
                HttpStatus.ACCEPTED,
                adminService.modifiedExistLawyerById(appUserRequest , lawyerId));
    }


    @DeleteMapping("/lawyers/{lawyerId}")
    public ResponseEntity<ApiResponse<Void>> removeExistLawyer(@PathVariable Long lawyerId ){
        return responseEntity(true,
                STR."Delete lawyer id \{lawyerId} successfully",
                HttpStatus.ACCEPTED,
                adminService.removeExistLawyerById(lawyerId));
    }
}
