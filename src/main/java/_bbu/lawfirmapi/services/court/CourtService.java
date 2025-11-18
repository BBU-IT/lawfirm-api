package _bbu.lawfirmapi.services.court;

import _bbu.lawfirmapi.models.DTO.court.request.CourtRequest;
import _bbu.lawfirmapi.models.DTO.court.response.CourtResponse;
import _bbu.lawfirmapi.models.Entity.Court;
import java.util.List;

public interface CourtService {
    Court getCourtById(Long courtId);
    List<Court> getCourtList();
    CourtResponse createNewCourt( CourtRequest courtRequest);
    CourtResponse modifiedCourtById(CourtRequest courtRequest , Long courtId);
    Void removeCourtById(Long courtId);
}
