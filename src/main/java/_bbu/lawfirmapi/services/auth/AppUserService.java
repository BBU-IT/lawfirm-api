package _bbu.lawfirmapi.services.auth;


import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {

//    void validateUserByEmail(String email);

    UserDetails loadUserByUsername(String email);

    List<AppUser> getAllUser();

//    AppUserResponse findByEmail(String email);

//    AppUserResponse findByEmail(String userEmail);

     AppUserResponse registerNewUser(AppUserRequest appUserRequest);

//    AppUserResponse getProfile();

//    void removeProfile();

//    String reSendOTP(String email);

//    AppUserResponse verifyOTP(String email, String otp, Boolean isOTPRegister);
//
//    String resetPassword(String email, String newPassword);

//    AppUserResponse updateProfile(@Valid AppUserUpdateRequest appUserUpdateRequest);
//
//    void updateUserPassword(@Valid PasswordUpdateRequest passwordUpdateRequest);
}
