package _bbu.lawfirmapi.services.admin.implement;

import _bbu.lawfirmapi.exceptions.EmailAlreadyExistException;
import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Expertise;
import _bbu.lawfirmapi.models.Entity.Role;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.ExpertiseRepository;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.admin.AdminService;
import _bbu.lawfirmapi.utils.MethodHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ExpertiseRepository expertiseRepository;
    private final MethodHelper checkOutOfPage;

    public AppUser getCurrentUser(){
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @Override
    public Page<AppUser> getAllUser(Pageable pageable , Integer requestPage){
        Page<AppUser> lawyerList = appUserRepository.findAllLawyers(pageable);
        checkOutOfPage.isInvalidPage(lawyerList.getTotalPages() , requestPage);
        if (lawyerList.isEmpty()){
            throw new NotFoundException("No appointment list here.");
        }
        return lawyerList ;
    }

    @Override
    public AppUser getLawyerById(Long lawyerId){
        return appUserRepository.findById(lawyerId).orElseThrow(() -> new NotFoundException("Lawyer with id " + lawyerId + " not found."));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser userDetail = appUserRepository.findByEmailWithRole(email);
        

        if (userDetail == null) {
            throw new UsernameNotFoundException("User does not exist");
        }
        return userDetail;
    }

    @Override
    public void checkIsEmailExist(String email){
        if (appUserRepository.findByEmailWithRole(email) != null) {
            throw new EmailAlreadyExistException("Email already exists: " + email);
        }
    }

    @Override
    public AppUserResponse registerNewLawyer(AppUserRequest appUserRequest) {
        System.out.println("My register request : " + appUserRequest);
        AppUser newLawyer = appUserRequest.toEntity();
        //  Check for existing email
        checkIsEmailExist(appUserRequest.getEmail());

        // Convert expertise IDs to entities
        Set<Expertise> expertiseEntities = appUserRequest.getExpertiseIdList().stream()
                .map(id -> expertiseRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Expertise with id " + id + " not found")))
                .collect(Collectors.toSet());

        // Prepare expertise get name only
        Set<String> setOfExpertiseName = expertiseEntities.stream()
                .map(Expertise::getExpertName)
                .collect(Collectors.toSet());

        System.out.println("Expertise list " +setOfExpertiseName);

        // Fetch role
        Role role = roleRepository.findById(appUserRequest.getRoleId())
                .orElseThrow(() -> new NotFoundException("Invalid role ID: " + appUserRequest.getRoleId()));

        newLawyer.setFullName(appUserRequest.getFullName());
        newLawyer.setGender(appUserRequest.getGender());
        newLawyer.setLawyerStatus(appUserRequest.getLawyerStatus());
        newLawyer.setEmail(appUserRequest.getEmail());
        newLawyer.setPhoneNumber(appUserRequest.getPhoneNumber());
        newLawyer.setPassword(passwordEncoder.encode(appUserRequest.getPassword()));
        newLawyer.setRole(role);
        newLawyer.setExpertises(expertiseEntities);
        newLawyer.setImage(appUserRequest.getImage());
        newLawyer.setDescription(appUserRequest.getDescription());
        newLawyer.setTitle(appUserRequest.getTitle());
        newLawyer.setFacebookLink(appUserRequest.getFacebookLink());
        newLawyer.setTiktokLink(appUserRequest.getTiktokLink());
        newLawyer.setTelegramLink(appUserRequest.getTelegramLink());

        AppUserResponse savedNewLawyer = appUserRepository.save(newLawyer).toResponse();
        return savedNewLawyer;
    }


    @Override
    public AppUserResponse modifiedExistLawyerById(AppUserRequest appUserRequest , Long lawyerId){
        checkIsEmailExist(appUserRequest.getEmail());

        AppUser currentLawyer = appUserRepository.findById(lawyerId).orElseThrow(
                () -> new NotFoundException("Lawyer Id " + lawyerId + " not found.")
        );

        // Attach existing Role
        Role role = roleRepository.findById(appUserRequest.getRoleId())
                .orElseThrow(() -> new NotFoundException("Invalid role ID: " + appUserRequest.getRoleId()));


        // Convert expertise IDs to entities
        Set<Expertise> expertiseEntities = appUserRequest.getExpertiseIdList().stream()
                .map(id -> expertiseRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Expertise with id " + id + " not found")))
                .collect(Collectors.toSet());

        // Prepare expertise get name only
        Set<String> setOfExpertiseName = expertiseEntities.stream()
                .map(Expertise::getExpertName)
                .collect(Collectors.toSet());

        currentLawyer.setFullName(appUserRequest.getFullName());
        currentLawyer.setEmail(appUserRequest.getEmail());
        currentLawyer.setGender(appUserRequest.getGender());
        currentLawyer.setLawyerStatus(appUserRequest.getLawyerStatus());
        currentLawyer.setPhoneNumber(appUserRequest.getPhoneNumber());
        currentLawyer.setPassword(appUserRequest.getPassword());
        currentLawyer.setImage(appUserRequest.getImage());
        currentLawyer.setDescription(appUserRequest.getDescription());
        currentLawyer.setTitle(appUserRequest.getTitle());
        currentLawyer.setFacebookLink(appUserRequest.getFacebookLink());
        currentLawyer.setTiktokLink(appUserRequest.getTiktokLink());
        currentLawyer.setTelegramLink(appUserRequest.getTelegramLink());
        currentLawyer.setRole(role);
        currentLawyer.setExpertises(expertiseEntities);

        AppUserResponse updatedLawyer = appUserRepository.save(currentLawyer).toResponse();

            return updatedLawyer;

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
