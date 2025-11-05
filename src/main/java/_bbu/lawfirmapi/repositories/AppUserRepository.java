package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u JOIN FETCH u.role WHERE u.email = :email")
    AppUser findByEmailWithRole(@Param("email") String email);


    // Fetch all lawyers with their roles
    @EntityGraph(attributePaths = {"role", "expertises"})
    @Query("SELECT u FROM AppUser u WHERE u.role.roleName = 'ROLE_LAWYER'")
    List<AppUser> findAllLawyers();


    // Fetch user with clients (if needed separately)
    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.clients WHERE u.appUserId = :id")
    Optional<AppUser> findByIdWithClients(@Param("id") Long id);

    // Fetch user with cases (if needed separately)
    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.cases WHERE u.appUserId = :id")
    Optional<AppUser> findByIdWithCases(@Param("id") Long id);

    // Basic existence check (no joins needed)
    boolean existsByEmail(String email);
}