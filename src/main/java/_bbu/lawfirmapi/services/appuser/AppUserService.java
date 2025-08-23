package _bbu.lawfirmapi.services.appuser;


import _bbu.lawfirmapi.models.DTO.appuer.res.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
public interface AppUserService {

    List<AppUser> getAllUser();
}
