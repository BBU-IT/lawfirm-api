package _bbu.lawfirmapi.services.auth.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.services.auth.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AppUser> getAllUser(){
        return appUserRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser userDetails = appUserRepository.findByEmail(username);
        if (userDetails == null) {
            throw new NotFoundException("User does not exist");
        }


        return userDetails;
    }
    //    @Override
//    public AppUserResponse getUserByEmail(String email){
//        return appUserRepository.getAppUserByEmail(email);
//    }

    @Override
    public AppUserResponse registerNewUser(AppUserRequest appUserRequest) {
        // Check for existing email
//        if (appUserRepository.findByEmail(appUserRequest.getEmail())) {
//            throw new IllegalArgumentException("Email already exists: " + appUserRequest.getEmail());
//        }

        // Create and populate AppUser
        AppUser user = AppUser.builder()
                .userName(appUserRequest.getUserName()) // Using email as username
                .email(appUserRequest.getEmail())
                .phoneNumber(appUserRequest.getPhoneNumber())
                .password(passwordEncoder.encode(appUserRequest.getPassword())) // Hash password
                .roleId(appUserRequest.getRoleId())  // Ensure type consistency
                .description(appUserRequest.getDescription())
                .build();

        // Save and map to response
        AppUser newUser = appUserRepository.save(user);
        return AppUserResponse.builder()
                .appUserId(newUser.getAppUserId())
                .userName(newUser.getName())
                .email(newUser.getUsername())
                .phoneNumber(newUser.getPhoneNumber())
                .password(newUser.getPassword())
                .roleId(newUser.getRoleId())
                .description(newUser.getDescription())
                .build(); // Exclude password from response
    }
//    public AppUserResponse insertNewUser(AppUserRequest appUserRequest){
//        System.out.println("from  service request " + appUserRequest);
//        return appUserRepository.insertNewUser(appUserRequest);
//    }
}
