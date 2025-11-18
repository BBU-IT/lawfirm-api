package _bbu.lawfirmapi.models.DTO.cases.request;

import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Case;
import _bbu.lawfirmapi.models.Entity.Client;
import _bbu.lawfirmapi.models.Entity.Court;
import _bbu.lawfirmapi.models.Enumerations.CaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseRequest {

    private Long clientId;
    private Long courtId;
    private Long appUserId;
    private String title;
    private String description;
    private CaseStatus status;
    private LocalDateTime statedDate;
    private LocalDateTime endedDate;

    public Case toEntity(Client client, Court court, AppUser appUser) {
        return new Case(
                null,
                client,
                court,
                appUser,
                this.title,
                this.description,
                this.status,
                this.statedDate,
                this.endedDate
        );
    }

}
