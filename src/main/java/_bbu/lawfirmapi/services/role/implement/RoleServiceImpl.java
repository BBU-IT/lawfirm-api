package _bbu.lawfirmapi.services.role.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import _bbu.lawfirmapi.models.Entity.Role;
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
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    @Override
    public Role findRoleByRoleId(Integer roleId){
       return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException(" Role id not found"));
    }
    @Override
    public RoleResponse createNewRoleList(RoleRequest newRoleRequest){
        Role role = new Role();
        role.setRoleName(newRoleRequest.getRoleName());
        Role newRole = roleRepository.save(role);
        return RoleResponse.builder()
                .roleId(newRole.getRoleId())
                .roleName(newRole.getRoleName())
                .build();
    }
    @Override
    public RoleResponse updateRoleById(Integer roleId  , RoleRequest updateRole){
        Role updateNewRole = roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("role Id "  + roleId + "not found"));
        updateNewRole.setRoleName(updateRole.getRoleName());
        Role savedRole = roleRepository.save(updateNewRole);
        return RoleResponse.builder()
                .roleId(savedRole.getRoleId())
                .roleName(savedRole.getRoleName())
                .build();
    }
    @Override
    public void deleteRoleById(Integer roleId){
         roleRepository.deleteById(roleId);
    }

}
