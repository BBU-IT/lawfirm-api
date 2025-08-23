package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.appuer.res.AppUser;
import _bbu.lawfirmapi.models.DTO.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.response.BaseResponse;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.services.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/lawyer")

public class AppUserController extends BaseResponse {

//    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppUser>>> getAllUser(){

        System.out.println("My Data List : " + appUserService.getAllUser());
        return responseEntity(true , "Get all user" , HttpStatus.OK , appUserService.getAllUser());
    }
}
