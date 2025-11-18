package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.repositories.AppointmentRepository;
import _bbu.lawfirmapi.services.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")

public class AppointmentController extends BaseResponse {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<Slice<Appointment>>> getAllAppointment(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ){

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Slice<Appointment> appointmentList = appointmentService.getAllAppointment(pageable);
        return responseEntity(true ,
                "Get appointment List"  ,
                HttpStatus.OK ,
                appointmentList);

    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createNewAppointment(@RequestBody AppointmentRequest appointmentRequest){
        return responseEntity(true ,
                "Create new appointment successfully" ,
                HttpStatus.CREATED ,
                appointmentService.createNewAppointment(appointmentRequest) );
    }
}
