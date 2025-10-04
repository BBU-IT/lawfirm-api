package _bbu.lawfirmapi.services.department;

import _bbu.lawfirmapi.models.DTO.department.request.DepartmentRequest;
import _bbu.lawfirmapi.models.DTO.department.response.DepartmentResponse;
import _bbu.lawfirmapi.models.DTO.role.request.RoleRequest;
import _bbu.lawfirmapi.models.DTO.role.response.RoleResponse;
import _bbu.lawfirmapi.models.Entity.Department;
import _bbu.lawfirmapi.models.Entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService  {
    List<DepartmentResponse> getAllDepartment();
    Department getDepartmentById(Long roleId);

    DepartmentResponse createNewDepartment (DepartmentRequest newDepartmentReq);

    DepartmentResponse updateDepartmentById(Long departmentId, DepartmentRequest departmentRequest);

    void deleteDepartmentById(Long departmentId);
}
