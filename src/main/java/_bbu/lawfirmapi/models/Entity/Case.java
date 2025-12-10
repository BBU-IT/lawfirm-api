package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.DTO.cases.response.CaseResponse;
import _bbu.lawfirmapi.models.Enumerations.CaseStatus;
import _bbu.lawfirmapi.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cases")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Case extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id" , referencedColumnName = "client_id")
    @ToString.Exclude
    private Client client;
    //  act like court_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id" , referencedColumnName = "court_id")
    @ToString.Exclude
    private Court court;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id" , referencedColumnName = "appuser_id")
    @ToString.Exclude
    private AppUser appUser;

    @OneToOne(mappedBy = "aCase")
    @ToString.Exclude
    @JsonIgnore

    private Appointment appointment;

    @Column(name = "title")
    private String title;

    @Column(name = "description"  ,columnDefinition = "TEXT")

    private String description;

    @Enumerated(EnumType.STRING)

    private CaseStatus status ;

    @Column(name = "start_date")

    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Case(Object o,
                Client client,
                Court court,
                AppUser appUser,
                String title,
                String description,
                CaseStatus status,
                LocalDateTime statedDate,
                LocalDateTime endedDate) {
    }


    public CaseResponse toResponse(){
        return new CaseResponse(this.caseId ,
                this.client ,
                this.appUser ,
                this.court ,
                this.title ,
                this.description ,
                this.status ,
                this.startDate ,
                this.endDate);
    }
}
