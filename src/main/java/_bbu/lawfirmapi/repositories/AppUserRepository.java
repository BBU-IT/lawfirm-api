package _bbu.lawfirmapi.repositories;

import _bbu.lawfirmapi.models.DTO.appuer.res.AppUser;
import org.apache.ibatis.annotations.*;
import java.util.List;
@Mapper
public interface AppUserRepository {

    @Results(id = "appUserMapper",
            value = {@Result(property = "appUserId", column = "app_user_id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "email" , column = "email"),
                    @Result(property = "phoneNumber" , column = "phone"),
                    @Result(property = "role" , column = "role_id" , one = @One(select = "getRoleById")),
                    @Result(property = "description" , column = "description")
            })
    @ResultMap("appUserMapper")
    @Select("""
			SELECT * FROM app_users
			""")
    public List<AppUser> getAllUser();

    @Select("""
			SELECT name FROM roles WHERE role_id = #{role_id}
			""")
    public String getRoleById(@Param("role_id") Integer roleId);
}
