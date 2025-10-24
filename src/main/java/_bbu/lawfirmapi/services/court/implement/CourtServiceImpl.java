package _bbu.lawfirmapi.services.court.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.court.request.CourtRequest;
import _bbu.lawfirmapi.models.DTO.court.response.CourtResponse;
import _bbu.lawfirmapi.models.Entity.Court;
import _bbu.lawfirmapi.repositories.CourtRepository;
import _bbu.lawfirmapi.services.court.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    @Override
    public List<Court> getCourtList() {
        return Optional.of(courtRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new NotFoundException("No court list found."));
    }
    @Override
    public CourtResponse createNewCourt(CourtRequest courtRequest) {

        Court newCourt = courtRequest.toEntity();
        newCourt.setCourtName(courtRequest.getCourtName());
        newCourt.setCourtType(courtRequest.getCourtType());
        newCourt.setContactNumber(courtRequest.getContactNumber());
        newCourt.setLocation(courtRequest.getLocation());
        newCourt.setCreatedAt(LocalDateTime.now());
        return courtRepository.save(newCourt).toResponse();

    }


}
