package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.Enumerations.CourtType;
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
@Table(name = "courts")
public class Court extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id")
    private Long courtId;

    @Column(name = "court_name")
    private String courtName;

    @Enumerated(EnumType.STRING)

    private CourtType courtType ;

    @Column(name = "location")

    private String location ;

    @Column(name = "contact_number")

    private String contactNumber ;
}
