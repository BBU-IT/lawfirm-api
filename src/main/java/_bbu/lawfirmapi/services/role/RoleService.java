package _bbu.lawfirmapi.services.role;

import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import _bbu.lawfirmapi.models.Entity.Role;

import java.util.List;

public interface RoleService  {
    // jpa
    List<Role> getAllRoles();
   Role findRoleByRoleId(Integer roleId);

    RoleResponse createNewRoleList (RoleRequest newRoleRequest);

    RoleResponse updateRoleById(Integer roleId, RoleRequest updateRole);

    void deleteRoleById(Integer roleId);


}
