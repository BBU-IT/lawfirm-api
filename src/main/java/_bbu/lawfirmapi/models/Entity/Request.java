package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.models.Enumerations.RequestStatus;
import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")

    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id" , referencedColumnName = "client_id")

    private Client client;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "request_detail")
    private String requestDetail;

    @Column(name = "status")
    private RequestStatus requestStatus;
}
