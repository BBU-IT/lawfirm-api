package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.utils.BaseEntity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true, exclude = {"expertises", "clients", "cases"})
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
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude

    @JoinColumn(name = "role_id" , referencedColumnName = "role_id")
    private Role role;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "appuser_expertise",
            joinColumns = @JoinColumn(name = "appuser_id"),
            inverseJoinColumns = @JoinColumn(name = "expertise_id")
    )
    @ToString.Exclude
    private Set<Expertise> expertises;
    @Column(name = "user_name")
    private String userName;
    @Column(name ="email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;
    @Column(name = "image" )
    private String image;

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

    public AppUser(Object o, String userName, String email, String phoneNumber, String password, Integer roleId, Set<Integer> expertiseIdList, String image, String description) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }

    public AppUserResponse toResponse(){
        return new AppUserResponse(
                this.appUserId,
                this.userName,
                this.email,
                this.phoneNumber,
                this.password,
                this.role.getRoleName(),
                this.expertises.stream().map(
                        Expertise::getExpertName
                ).collect(Collectors.toSet()),
                this.image,
                this.description
        );
    }


    @Override
    public String getUsername() {
        return email;
    }

    public String getName(){
        return userName;
    }


}


