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
@Table(name = "appuser_requests")
public class UserRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_request_id")

    private Long userRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser_id" , referencedColumnName = "appuser_id")

    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id" , referencedColumnName = "request_id")

    private Request request;
}
