package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.appointment.request.AppointmentRequest;
import _bbu.lawfirmapi.models.DTO.appointment.response.AppointmentResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Appointment;
import _bbu.lawfirmapi.repositories.AppointmentRepository;
import _bbu.lawfirmapi.services.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<List<Appointment>>> getAllAppointment(){

        return responseEntity(true ,
                "Get appointment List"  ,
                HttpStatus.OK ,
                appointmentService.getAllAppointment());

    }

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> createNewAppointment(@RequestBody AppointmentRequest appointmentRequest){
        return responseEntity(true ,
                "Create new appointment successfully" ,
                HttpStatus.CREATED ,
                appointmentService.createNewAppointment(appointmentRequest) );
    }
}
