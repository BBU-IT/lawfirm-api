package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseResponse {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles(){
//        return responseEntity(true, "Getting role using Data JPA" , HttpStatus.OK , roleService.getAllRoles());
//    }
    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Integer roleId){
//        System.out.println("My Data" + roleService.findRoleByRoleId(roleId));
        return responseEntity(true , "Getting RoleResponse " + roleService.findRoleByRoleId(roleId).toString() , HttpStatus.OK , roleService.findRoleByRoleId(roleId));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createNewRoleList(@RequestBody RoleRequest roleRequest){
        System.out.println("my data" + roleRequest);
        return responseEntity(true , "Created new role success" , HttpStatus.CREATED ,roleService.createNewRoleList(roleRequest));
    }
//    public ResponseEntity.
//    @DeleteMapping("/{roleId}")
//    public ResponseEntity<ApiResponse<Void>> deleteRoleById(@PathVariable Integer roleId){
//            return responseEntity(true , "Delete success" , HttpStatus.OK , roleService.deleteRoleById(roleId));
//    }
    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateNewRole (@PathVariable Integer roleId , @RequestBody RoleRequest newRoleRequest) {
        return responseEntity(true, "update role success", HttpStatus.ACCEPTED, roleService.updateRoleById(roleId, newRoleRequest));
    }
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Integer roleId) {
        if (!roleRepository.existsById(roleId)) {
            return ResponseEntity.notFound().build();
        }
        roleRepository.deleteById(roleId);
        return ResponseEntity.noContent().build(); // 204
    }




}
