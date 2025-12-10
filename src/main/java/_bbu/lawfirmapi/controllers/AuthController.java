package _bbu.lawfirmapi.controllers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import _bbu.lawfirmapi.exceptions.InvalidException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.DTO.auth.request.AuthRequest;
import _bbu.lawfirmapi.models.DTO.auth.response.AuthResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.services.admin.AdminService;
import _bbu.lawfirmapi.jwt.JwtService;
import _bbu.lawfirmapi.utils.MethodHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
@Validated
public class AuthController extends BaseResponse {
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AdminService adminService;
    private final MethodHelper helper;


    private void authenticate(String email , String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RuntimeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new InvalidException(
                    "Invalid username, email, or password. Please check your credentials and try again.");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) throws Exception {
        final UserDetails userDetails = adminService.loadUserByUsername(request.getEmail());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("My auth login " +  userDetails);
        authenticate(userDetails.getUsername() ,  request.getPassword());
       final String token = jwtService.generateToken(userDetails);
        final String expiredTokenDateTime = helper.extractExpirationDateInCambodia(token);
        AuthResponse authResponse = new AuthResponse(token , userDetails ,expiredTokenDateTime );

        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder().success(true)
                .message("Login Successfully").status(HttpStatus.OK).code(HttpStatus.OK.value())
                .payload(authResponse).timestamps(LocalDateTime.now()).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping( "/register" )
//    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Register New User", description = "Registers a new user and returns user details")
    public ResponseEntity<ApiResponse<AppUserResponse>> register( @RequestBody AppUserRequest request) {
        return responseEntity(true ,
                "Create new lawyer successfully.",
                HttpStatus.CREATED,
                adminService.registerNewLawyer(request));
    }
}