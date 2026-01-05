package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.DTO.expertise.response.ExpertiseResponse;
import _bbu.lawfirmapi.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true , onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "expertises")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// custom response in JSON
@JsonPropertyOrder({"expertiseId" , "expertName" , "createdAt" , "updatedAt"})
public class Expertise extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "expertise_id")
    private Integer expertiseId;

    @Column(name = "expert_name")
    private String expertName;

    @ManyToMany(mappedBy = "expertises" )
    @JsonIgnore
    private Set<AppUser> lawyerProfiles;

    @OneToMany(mappedBy = "expertise" , cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Service> serviceSet;

    public Expertise(Object o, String expertiseName) {
    }

    public ExpertiseResponse toResponse(){
        return new ExpertiseResponse(this.expertiseId , this.expertName, this.getCreatedAt() , this.getUpdatedAt() );
    }
}
