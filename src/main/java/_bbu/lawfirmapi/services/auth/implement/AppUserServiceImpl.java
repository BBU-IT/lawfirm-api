package _bbu.lawfirmapi.services.auth.implement;

import _bbu.lawfirmapi.exceptions.EmailAlreadyExistException;
import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Department;
import _bbu.lawfirmapi.models.Entity.Role;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.DepartmentRepository;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.auth.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
//@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

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
        if (appUserRepository.findByEmail(appUserRequest.getEmail()) != null) {
            throw new EmailAlreadyExistException("Email already exists: " + appUserRequest.getEmail());
        }

        // Attach existing Role
        Role role = roleRepository.findById(appUserRequest.getRoleId())
                .orElseThrow(() -> new NotFoundException("Invalid role ID: " + appUserRequest.getRoleId()));

        // Attach existing Department (if required)
        Department department = null;
        if (appUserRequest.getDepartmentId() != null) {
            department = departmentRepository.findById(appUserRequest.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid department ID: " + appUserRequest.getDepartmentId()));
        }

        // Create and populate AppUser
        AppUser user = AppUser.builder()
                .userName(appUserRequest.getUserName())
                .email(appUserRequest.getEmail())
                .phoneNumber(appUserRequest.getPhoneNumber())
                .password(passwordEncoder.encode(appUserRequest.getPassword()))
                .role(role)               // managed entity ✅
                .department(department)   // managed entity ✅
                .description(appUserRequest.getDescription())
                .build();

        // Save
        AppUser newUser = appUserRepository.save(user);

        System.out.println("New User " + newUser);
        // Map to response
        return AppUserResponse.builder()
                .appUserId(newUser.getAppUserId())
                .userName(newUser.getName())
                .email(newUser.getUsername())
                .phoneNumber(newUser.getPhoneNumber())
                .roleId(newUser.getRole().getRoleId())
                .description(newUser.getDescription())
                .build(); // don’t expose password in response
    }

//    public AppUserResponse insertNewUser(AppUserRequest appUserRequest){
//        System.out.println("from  service request " + appUserRequest);
//        return appUserRepository.insertNewUser(appUserRequest);
//    }
}
