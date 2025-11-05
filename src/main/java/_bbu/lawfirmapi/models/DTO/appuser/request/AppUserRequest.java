package _bbu.lawfirmapi.models.DTO.appuser.request;
import _bbu.lawfirmapi.models.Entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserRequest {
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private Integer roleId;
    private Set<Integer> expertiseIdList;
    private String image ;
    private String description;

    public AppUser toEntity(){
        return new AppUser(null ,
                this.userName ,
                this.email ,
                this.phoneNumber ,
                this.password ,
                this.roleId ,
                this.expertiseIdList ,
                this.image,
                this.description);
    }

}
