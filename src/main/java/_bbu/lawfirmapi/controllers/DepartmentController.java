package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.department.request.DepartmentRequest;
import _bbu.lawfirmapi.models.DTO.department.response.DepartmentResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Department;
import _bbu.lawfirmapi.services.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Validated
public class DepartmentController extends BaseResponse {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getDepartmentList(){
        return responseEntity(true , "Get all departments " , HttpStatus.OK , departmentService.getAllDepartment());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(@PathVariable Long id){
        if(departmentService.getDepartmentById(id) == null){
            return responseEntity(false , "Department id " + id + " not found" , HttpStatus.NOT_FOUND , null);
        }
        return responseEntity(true , "Get department id " + id + " successfully" , HttpStatus.OK , departmentService.getDepartmentById(id));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createNewDepartment(@RequestBody DepartmentRequest departmentRequest){
        return responseEntity(true , "Create new department successfully" , HttpStatus.CREATED , departmentService.createNewDepartment(departmentRequest));
    }

}
