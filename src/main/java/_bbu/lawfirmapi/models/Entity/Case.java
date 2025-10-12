package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.Enumerations.CaseStatus;
import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cases")
public class Case extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id" , referencedColumnName = "client_id")
    private Client client;
    //  act like court_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id" , referencedColumnName = "court_id")
    private Court court;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id" , referencedColumnName = "appuser_id")
    private AppUser appUser;
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

}
