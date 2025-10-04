package _bbu.lawfirmapi.services.department.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.department.request.DepartmentRequest;
import _bbu.lawfirmapi.models.DTO.department.response.DepartmentResponse;
import _bbu.lawfirmapi.models.Entity.Department;
import _bbu.lawfirmapi.repositories.DepartmentRepository;
import _bbu.lawfirmapi.services.department.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final Department newDepartment = new Department();
    @Override

    public List<DepartmentResponse> getAllDepartment(){
        List<Department> departmentList = departmentRepository.findAll();

        return departmentList.stream()
                .map(department -> DepartmentResponse.builder()
                        .departmentId(department.getDepartmentId())
                        .departmentName(department.getDepartmentName())
                        // add other fields from your Department entity
                        .build())
                .collect(Collectors.toList());

    }
    @Override
    public Department getDepartmentById(Long departmentId){
        return departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException("this Department id " +  departmentId + " not found "));
    }
    @Override
    public  DepartmentResponse createNewDepartment(DepartmentRequest departmentRequest){

         newDepartment.setDepartmentName(departmentRequest.getDepartmentName());
         departmentRepository.save(newDepartment);
        return DepartmentResponse
                .builder()
                .departmentId(newDepartment.getDepartmentId())
                .departmentName(newDepartment.getDepartmentName())
                .build();
    }
    @Override
    public DepartmentResponse updateDepartmentById(Long departmentId , DepartmentRequest departmentRequest){

        departmentRepository.findById(departmentId).orElseThrow(() -> new NotFoundException("this Department id " +  departmentId + " not found "));
        newDepartment.setDepartmentName(departmentRequest.getDepartmentName());

        return DepartmentResponse
                .builder()
                .departmentId(departmentId)
                .departmentName(newDepartment.getDepartmentName())
                .build();
    }
    @Override
    public void deleteDepartmentById(Long departmentId){
        departmentRepository.deleteById(departmentId);
    }
}
