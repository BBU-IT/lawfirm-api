package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.utils.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "app_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class AppUser extends BaseEntity implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appuser_id")
    private Long appUserId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id" , referencedColumnName = "role_id")
    @ToString.Exclude
    private Role role;
    @Column(name = "user_name")
    private String userName;
    @Column(name ="email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column (name = "description" , columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "appUser" , fetch = FetchType.LAZY)
    @JsonIgnore  // Add this
    @ToString.Exclude
    private List<Case> cases;

    @OneToMany(mappedBy = "appUser" , fetch = FetchType.LAZY)
    @JsonIgnore  // Add this
    @ToString.Exclude
    private List<Client> clients;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    public String getName(){
        return userName;
    }


}


