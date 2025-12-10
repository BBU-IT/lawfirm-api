package _bbu.lawfirmapi.controllers;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.services.admin.AdminService;
import _bbu.lawfirmapi.services.auth.AppUserService;
import _bbu.lawfirmapi.services.lawyer.LawyerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lawyers")

public class LawyerController extends BaseResponse {

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final AdminService adminService;
    private final LawyerService lawyerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppUser>>> getAllUser(){


        return responseEntity(true ,
                "Get all Lawyers successfully." ,
                HttpStatus.OK ,
                lawyerService.fetchAllLawyers());
    }
    @GetMapping("/{lawyerId}")
    public ResponseEntity<ApiResponse<AppUser>> fetchLawyerById(@PathVariable Long lawyerId){
        return responseEntity(true ,
                "Get lawyer with id " + lawyerId + " successfully.",
                HttpStatus.ACCEPTED,
                lawyerService.fetchLawyerById(lawyerId)
        );
    }
    @PutMapping("/{lawyerId}")
    public ResponseEntity<ApiResponse<AppUserResponse>> updateExistLawyerById(@RequestBody AppUserRequest appUserRequest , @PathVariable Long lawyerId ){
        return responseEntity(true,
                "Update lawyer id " + lawyerId + " successfully" ,
                HttpStatus.OK,
                adminService.modifiedExistLawyerById(appUserRequest , lawyerId));
    }
    @DeleteMapping("/{lawyerId}")
    public ResponseEntity<ApiResponse<Void>> removeExistLawyer(@PathVariable Long lawyerId ){
        return responseEntity(true,
                "Delete laywer id " + lawyerId+  " successfully",
                HttpStatus.OK,
                adminService.removeExistLawyerById(lawyerId));
    }
    // get user by gmail
//    @GetMapping("/{email}")
//    public ResponseEntity<ApiResponse<AppUserResponse>> getUserByEmail(@PathVariable  String email){
//        return responseEntity(true , "User name " + appUserService.getUserByEmail(email).getName() , HttpStatus.OK , appUserService.getUserByEmail(email));
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<AppUserResponse>> insertNewUser(@RequestBody UserRequest request){
//
//        System.out.println("My new user request from ui " + request);
//        return responseEntity(true , "Create new user successfully" , HttpStatus.CREATED , appUserService.insertNewUser(request));
//    }

}
