package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleResponse, Integer> {

}