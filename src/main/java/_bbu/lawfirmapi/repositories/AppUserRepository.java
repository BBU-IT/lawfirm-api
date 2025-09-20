package _bbu.lawfirmapi.repositories;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.repositories.RoleRepository;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// 👇 Recommended Code
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    // No @Query needed! Spring Data JPA creates the query from the method name.
     @Query(value = "select * from app_users where email = ?" , nativeQuery = true)
     AppUser findByEmail(String email);

}