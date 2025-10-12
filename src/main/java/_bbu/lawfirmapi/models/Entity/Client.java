package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "client_id")
    private Long  clientId ;
    @Column(name = "client_name")
    private String clientName ;
    @Column(name = "email")
    private String email ;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name =  "message" , columnDefinition = "TEXT")
    private String message ;

}
