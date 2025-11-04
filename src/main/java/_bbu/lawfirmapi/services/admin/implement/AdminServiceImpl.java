package _bbu.lawfirmapi.services.admin.implement;

import _bbu.lawfirmapi.exceptions.EmailAlreadyExistException;
import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Role;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<AppUser> getAllUser(){
        System.out.println("All lawyer " + appUserRepository.findAllLawyers());
        return appUserRepository.findAllLawyers();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser userDetail = appUserRepository.findByEmailWithRole(email);


        System.out.println("load user " + userDetail);
        if (userDetail == null) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return userDetail;
    }

    //    @Override
//    public AppUserResponse getUserByEmail(String email){
//        return appUserRepository.getAppUserByEmail(email);
//    }
    @Override
    public void checkIsEmailExist(String email){
        if (appUserRepository.findByEmailWithRole(email) != null) {
            throw new EmailAlreadyExistException("Email already exists: " + email);
        }
    }

    @Override
    public AppUserResponse registerNewLawyer(AppUserRequest appUserRequest) {
        // Check for existing email
        checkIsEmailExist(appUserRequest.getEmail());

        // Attach existing Role
        Role role = roleRepository.findById(appUserRequest.getRoleId())
                .orElseThrow(() -> new NotFoundException("Invalid role ID: " + appUserRequest.getRoleId()));


        // Create and populate AppUser
        AppUser user = AppUser.builder()
                .userName(appUserRequest.getUserName())
                .email(appUserRequest.getEmail())
                .phoneNumber(appUserRequest.getPhoneNumber())
                .password(passwordEncoder.encode(appUserRequest.getPassword()))
                .role(role)
                .image(appUserRequest.getImage())
                .description(appUserRequest.getDescription())
                .build();

        // Save
        AppUser updatedLawyer = appUserRepository.save(user);

        // Map to response
        return AppUserResponse.builder()
                .appUserId(updatedLawyer.getAppUserId())
                .userName(updatedLawyer.getName())
                .email(updatedLawyer.getUsername())
                .phoneNumber(updatedLawyer.getPhoneNumber())
                .password(passwordEncoder.encode(updatedLawyer.getPassword()))
                .role(updatedLawyer.getRole().getRoleName().substring(4))
                .description(updatedLawyer.getDescription())
                .image(updatedLawyer.getImage())
                .build(); // don’t expose password in response
    }
    @Override
    public AppUserResponse modifiedExistLawyerById(AppUserRequest appUserRequest , Long lawyerId){
        checkIsEmailExist(appUserRequest.getEmail());

        // Attach existing Role
        Role role = roleRepository.findById(appUserRequest.getRoleId())
                .orElseThrow(() -> new NotFoundException("Invalid role ID: " + appUserRequest.getRoleId()));


        // Create and populate AppUser
        AppUser user = AppUser.builder()
                .userName(appUserRequest.getUserName())
                .email(appUserRequest.getEmail())
                .phoneNumber(appUserRequest.getPhoneNumber())
                .password(passwordEncoder.encode(appUserRequest.getPassword()))
                .role(role)
                .image(appUserRequest.getImage())
                .description(appUserRequest.getDescription())
                .build();

        // Save
        AppUser updatedLawyer = appUserRepository.save(user);

        // Map to response
        return AppUserResponse.builder()
                .appUserId(updatedLawyer.getAppUserId())
                .userName(updatedLawyer.getName())
                .email(updatedLawyer.getUsername())
                .phoneNumber(updatedLawyer.getPhoneNumber())
                .password(passwordEncoder.encode(updatedLawyer.getPassword()))
                .role(updatedLawyer.getRole().getRoleName().substring(4))
                .description(updatedLawyer.getDescription())
                .image(updatedLawyer.getImage())
                .build();
                
    }

    @Override
    public Void removeExistLawyerById(Long appUserId){
        if(!appUserRepository.existsById(appUserId)){
          throw new NotFoundException("Lawyer id " + appUserId + " not found.");
        }
            appUserRepository.deleteById(appUserId);
        return null;
    }
}
