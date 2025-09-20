package _bbu.lawfirmapi.models.Entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "app_users")
@JsonPropertyOrder({
        "appUserId",
        "userName",
        "email",
        "phoneNumber",
        "password",
        "roleId",
        "description"
})
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id")
    private Long appUserId;
    @Column(name = "user_name")
    private String userName;
    @Column(name ="email")
    private String email;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id")
    private String roleId;


    @Column (name = "description")
    private String description;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(roleId));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getName(){
        return userName;
    }



}


