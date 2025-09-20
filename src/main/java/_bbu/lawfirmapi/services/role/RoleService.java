package _bbu.lawfirmapi.services.role;

import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import java.util.List;

public interface RoleService  {
    // jpa
    List<RoleResponse> getAllRoles();
    RoleResponse findRoleByRoleId(Integer roleId);

    RoleResponse createNewRoleList (RoleRequest newRoleRequest);

    RoleResponse updateRoleById(Integer roleId, RoleRequest updateRole);

    void deleteRoleById(Integer roleId);


}
