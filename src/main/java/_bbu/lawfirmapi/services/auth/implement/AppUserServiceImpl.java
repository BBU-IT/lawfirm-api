package _bbu.lawfirmapi.services.auth.implement;

import _bbu.lawfirmapi.exceptions.EmailAlreadyExistException;
import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Role;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.RoleRepository;
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
//@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

}
