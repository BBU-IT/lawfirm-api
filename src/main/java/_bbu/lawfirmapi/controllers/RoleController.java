package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Role;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseResponse {

    private final RoleService  roleService;
    private final RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Role>>> getAllRoles(){
        System.out.println(roleService.getAllRoles().isEmpty());
        return responseEntity(true ,
                "Getting all role" ,
                        HttpStatus.ACCEPTED,
                        roleService.getAllRoles());
    }
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Role>> getRoleById(@PathVariable Integer roleId){
//        System.out.println("My Data" + roleService.findRoleByRoleId(roleId));
        return responseEntity(true,
                "Getting Role " + roleService.findRoleByRoleId(roleId).getRoleName() + " success" ,
                HttpStatus.OK ,
                roleService.findRoleByRoleId(roleId));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createNewRoleList(@RequestBody RoleRequest roleRequest){
        System.out.println("my data" + roleRequest);
        return responseEntity(true , "Created new role success" , HttpStatus.CREATED ,roleService.createNewRoleList(roleRequest));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateNewRole (@PathVariable Integer roleId , @RequestBody RoleRequest newRoleRequest) {
        System.out.println("My updating role " + newRoleRequest);
        return responseEntity(true, "update role success", HttpStatus.ACCEPTED, roleService.updateRoleById(roleId, newRoleRequest));
    }
    @DeleteMapping("/{roleId}")
    public void deleteRoleById(@PathVariable Integer roleId) {
       roleService.removeRoleById(roleId);
    }


}
