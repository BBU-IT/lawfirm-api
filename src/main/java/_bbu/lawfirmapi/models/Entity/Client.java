package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.DTO.cases.response.CaseResponse;
import _bbu.lawfirmapi.models.DTO.client.response.ClientResponse;
import _bbu.lawfirmapi.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "client_id")
    private Long  clientId ;
    @Column(name = "client_name")
    private String clientName ;
    @Column(name = "email")
    private String email ;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name =  "message" , columnDefinition = "TEXT")
    private String message ;

    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL)
    @JsonIgnore  // Add this
    @ToString.Exclude
    private List<Case> cases;

    public Client(Object o, String clientName, String email, String phoneNumber, String address, String message) {
    }


    public ClientResponse toResponse(){
        return new ClientResponse(this.clientId , this.clientName , this.email , this.phoneNumber,  this.address , this.message );
    }
}
