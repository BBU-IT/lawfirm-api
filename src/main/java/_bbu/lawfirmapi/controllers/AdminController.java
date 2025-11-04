package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.services.admin.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")

public class AdminController extends BaseResponse {
    private final AdminService adminService;

    @GetMapping("/lawyers")
    public ResponseEntity<ApiResponse<List<AppUser>>> getAllUser(){
        return responseEntity(true , "Get all user" , HttpStatus.OK , adminService.getAllUser());
    }
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/lawyers/{lawyerId}")
    public ResponseEntity<ApiResponse<AppUserResponse>> updateExistLawyerById(@RequestBody AppUserRequest appUserRequest , @PathVariable Long lawyerId ){
        return responseEntity(true,
                "Update lawyer id " + lawyerId + " successfully" ,
                HttpStatus.OK,
                adminService.modifiedExistLawyerById(appUserRequest , lawyerId));
    }
    @SecurityRequirement(name = "bearerAuth")

    @DeleteMapping("/lawyers/{lawyerId}")
    public ResponseEntity<ApiResponse<Void>> removeExistLawyer(@PathVariable Long lawyerId ){
        return responseEntity(true,
                "Delete lawyer id " + lawyerId+  " successfully",
                HttpStatus.OK,
                adminService.removeExistLawyerById(lawyerId));
    }
}
