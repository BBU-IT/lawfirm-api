package _bbu.lawfirmapi.repositories;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.repositories.RoleRepository;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// Recommended Code
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.cases WHERE u.email = :email")
    AppUser findByEmailWithRole(@Param("email") String email);

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.clients WHERE u.appUserId = :id")
    AppUser findByIdWithClients(@Param("id") Long id);

}