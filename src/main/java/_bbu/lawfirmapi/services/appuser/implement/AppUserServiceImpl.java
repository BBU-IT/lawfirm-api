package _bbu.lawfirmapi.services.appuser.implement;

import _bbu.lawfirmapi.models.DTO.appuer.res.AppUser;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.services.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;


    public List<AppUser> getAllUser(){
        return appUserRepository.getAllUser();
    }


}
