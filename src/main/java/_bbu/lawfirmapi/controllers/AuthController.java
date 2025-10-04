package _bbu.lawfirmapi.controllers;

import java.time.LocalDateTime;
import java.util.Objects;

import _bbu.lawfirmapi.exceptions.InvalidException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.DTO.auth.request.AuthRequest;
import _bbu.lawfirmapi.models.DTO.auth.response.AuthResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.services.auth.AppUserService;
import _bbu.lawfirmapi.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
@Validated
public class AuthController extends BaseResponse {
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


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

        final UserDetails userDetails = appUserService.loadUserByUsername(request.getEmail());


        authenticate(userDetails.getUsername() ,  request.getPassword());;
//        appUserService.validateUserByEmail(userDetails.getUsername());
        String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);

        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder().success(true)
                .message("Login Successfully").status(HttpStatus.OK).code(HttpStatus.OK.value())
                .payload(authResponse).timestamps(LocalDateTime.now()).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Register New User", description = "Registers a new user and returns user details")
    public ResponseEntity<ApiResponse<AppUserResponse>> register(@Valid @RequestBody AppUserRequest request) {
        try {
            AppUserResponse appUserResponse = appUserService.registerNewUser(request);
            ApiResponse<AppUserResponse> response = ApiResponse.<AppUserResponse>builder()
                    .success(true)
                    .message("User registered successfully")
                    .payload(appUserResponse)
                    .status(HttpStatus.CREATED)
                    .code(HttpStatus.CREATED.value())
                    .timestamps(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
//            logger.error("Registration error for email: {}", request.getEmail(), ex);
            ApiResponse<AppUserResponse> errorResponse = ApiResponse.<AppUserResponse>builder()
                    .success(false)
                    .message("An error occurred during registration")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .timestamps(LocalDateTime.now())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}