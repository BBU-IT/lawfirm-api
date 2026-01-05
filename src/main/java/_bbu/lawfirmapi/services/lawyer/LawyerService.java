package _bbu.lawfirmapi.services.lawyer;

import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LawyerService {

    List<AppUserResponse> fetchAllLawyers ();

    AppUser fetchLawyerById(Long lawyerId);

    Void changeLawyerPasswordByEmail( String newPassword , String email);



}
