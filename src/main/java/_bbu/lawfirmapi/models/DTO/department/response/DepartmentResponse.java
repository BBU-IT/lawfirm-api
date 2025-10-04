package _bbu.lawfirmapi.models.DTO.department.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponse {
    private Long departmentId;
    private String departmentName;
}
