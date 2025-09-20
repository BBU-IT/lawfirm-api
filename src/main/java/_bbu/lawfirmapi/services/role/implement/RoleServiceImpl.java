package _bbu.lawfirmapi.services.role.implement;

import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
//    private final RoleWithMyBatis roleWithMyBatis;
    // get all role method
    @Override
    public List<RoleResponse> getAllRoles(){
        return roleRepository.findAll();
    }
    @Override
    public RoleResponse findRoleByRoleId(Integer roleId){
       return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Not found"));
    }
    @Override
    public RoleResponse createNewRoleList(RoleRequest newRoleRequest){
        RoleResponse role = new RoleResponse();
        role.setRoleName(newRoleRequest.getRoleName());
        return roleRepository.save(role);
    }
    @Override
    public RoleResponse updateRoleById(Integer roleId  , RoleRequest updateRole){
        return roleRepository.findById(roleId).map(ent -> {
            ent.setRoleName(updateRole.getRoleName());
            return  roleRepository.save(ent);
        }).orElseThrow(() -> new RuntimeException("RoleResponse id" + roleId + " not found"));
    }
    @Override
    public void deleteRoleById(Integer roleId){
         roleRepository.deleteById(roleId);
    }

}
