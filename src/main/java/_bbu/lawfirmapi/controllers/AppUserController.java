package _bbu.lawfirmapi.controllers;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.services.auth.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/lawyer")

public class AppUserController extends BaseResponse {

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppUser>>> getAllUser(){
        return responseEntity(true , "Get all user" , HttpStatus.OK , appUserService.getAllUser());
    }
    // get user by gmail
//    @GetMapping("/{email}")
//    public ResponseEntity<ApiResponse<AppUserResponse>> getUserByEmail(@PathVariable  String email){
//        return responseEntity(true , "User name " + appUserService.getUserByEmail(email).getName() , HttpStatus.OK , appUserService.getUserByEmail(email));
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<AppUserResponse>> insertNewUser(@RequestBody AppUserRequest request){
//
//        System.out.println("My new user request from ui " + request);
//        return responseEntity(true , "Create new user successfully" , HttpStatus.CREATED , appUserService.insertNewUser(request));
//    }

}
