package _bbu.lawfirmapi.models.Entity;

import _bbu.lawfirmapi.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "request_services")
public class RequestService extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_service_id")

    private Long requestServiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id" , referencedColumnName = "request_id")

    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id" , referencedColumnName = "service_id")

    private Service service;
}
