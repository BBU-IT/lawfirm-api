package _bbu.lawfirmapi.models.Entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id")
    private Long appUserId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id" , referencedColumnName = "role_id")
    @ToString.Exclude
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ToString.Exclude
    private Department department;
    @Column(name = "user_name")
    private String userName;
    @Column(name ="email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column (name = "description")
    private String description;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    @Column (name = "update_at")

    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.getRoleId().toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getName(){
        return userName;
    }

    // will calls onCreate(), setting both createdAt and updatedAt
    @PrePersist
    protected void onCreateNewUser(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    //will calls onUpdate() to refresh only updatedAt
    @PreUpdate
    protected void onUpdateUser(){
        this.updatedAt = LocalDateTime.now();
    }


}


