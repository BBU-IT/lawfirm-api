package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.Entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u LEFT JOIN FETCH u.expertises JOIN FETCH u.role WHERE u.email = :email")
    AppUser findByEmailWithRole(@Param("email") String email);
    // Fetch all lawyers with their roles
    // for admin
    @EntityGraph(attributePaths = {"role", "expertises"})
    @Query("SELECT u FROM AppUser u WHERE u.role.roleName = 'ROLE_LAWYER'")
    Page<AppUser> findAllWithExpertisesAndPagination(Pageable pageable);
//    @EntityGraph(attributePaths = {"role", "expertises"})
    @Query("SELECT u FROM AppUser u JOIN FETCH u.expertises WHERE u.role.roleName = 'ROLE_LAWYER'")
    List<AppUser> findAllWithExpertises();


    @Query("SELECT u FROM AppUser u JOIN FETCH u.expertises WHERE u.role.roleName = 'ROLE_LAWYER'")
    List<AppUser> findAllWithExpertisesNoPagination();
    @Query("SELECT u FROM AppUser u WHERE u.role.roleName = 'ROLE_LAWYER' AND u.appUserId = :lawyerId ")
    Optional<AppUser> findLawyerByAppUserId(@Param("lawyerId") Long lawyerId);

    // Basic existence check (no joins needed)
    boolean existsByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE AppUser a SET a.password = :newPassword WHERE a.email = :email ")

    int resetPassword(@Param("newPassword") String newPassword , @Param("email") String email );

    @Query("SELECT u FROM AppUser u JOIN FETCH u.expertises WHERE u.email = :email")
    Optional<AppUser> findAppUserByEmail(@Param("email") String email);

    @Query("""
    SELECT a
    FROM AppUser a
    JOIN a.role r
    JOIN a.expertises e
    WHERE r.roleName = 'ROLE_LAWYER'
      AND (
            LOWER(a.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
         OR LOWER(a.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
         OR LOWER(a.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
         OR LOWER(e.expertName) LIKE LOWER(CONCAT('%', :keyword, '%'))
         
      )
""")
    List<AppUser> searchLawyersByKeyword(@Param("keyword") String keyword);
}