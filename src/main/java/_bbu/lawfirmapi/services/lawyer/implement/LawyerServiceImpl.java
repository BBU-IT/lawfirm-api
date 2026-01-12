package _bbu.lawfirmapi.services.lawyer.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Task;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.TaskRepository;
import _bbu.lawfirmapi.services.lawyer.LawyerService;
import _bbu.lawfirmapi.utils.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LawyerServiceImpl implements LawyerService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskRepository taskRepo;
    private final MethodHelper checkIsOutOfPage;
    @Transactional(readOnly = true)
    public List<AppUserResponse> fetchAllLawyers() {

        List<AppUser> users = appUserRepository.findAllWithExpertises();

        return users.stream()
                .map(AppUser::toResponse)
                .toList();
    }
    @Override
    public AppUser fetchLawyerById(Long lawyerId){
        if(appUserRepository.findLawyerByAppUserId(lawyerId).isEmpty()){
            throw new NotFoundException("Lawyer with id " + lawyerId + " not found.");

        }
        return appUserRepository.findLawyerByAppUserId(lawyerId)
                .orElseThrow(
                        () -> new NotFoundException("Sorry lawyer with id " + lawyerId + " not found.")
                );
    }
    @Override
    public Page<Task> getTaskByLawyerEmail(Pageable pageable,Integer requestPage , String email ){
        Page<Task> tasks = taskRepo.findTaskByLawyerEmail(pageable , email);
        if(tasks.isEmpty()){
            throw new NotFoundException("List Of Task not found.");
        }
        checkIsOutOfPage.isInvalidPage(tasks.getTotalPages() , requestPage);
        return tasks;
    }

    @Override
    public Void changeLawyerPasswordByEmail(String newPassword , String email){


        String newPasswordEncoder = passwordEncoder.encode(newPassword);

        int updatedNewPassword = appUserRepository.resetPassword(newPasswordEncoder , email);

        if(updatedNewPassword == 0){
            throw new RuntimeException("User not found");
        }

        return null;

    }



}
