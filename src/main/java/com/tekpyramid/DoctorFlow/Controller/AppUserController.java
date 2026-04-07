package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Dto.DoctorSignUpDTO;
import com.tekpyramid.DoctorFlow.Dto.LoginRequest;
import com.tekpyramid.DoctorFlow.Dto.UserSignUpDTO;
import com.tekpyramid.DoctorFlow.Response.ApiResponse;
import com.tekpyramid.DoctorFlow.Response.Success;
import com.tekpyramid.DoctorFlow.Service.DoctorService;
import com.tekpyramid.DoctorFlow.Service.UserService;
import com.tekpyramid.DoctorFlow.JwtUtils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Slf4j
public class AppUserController {

    private final DoctorService doctorService;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    // 🔹 Doctor Registration
// 🔹 Doctor Registration
    @PostMapping("/doctor/register")
    public ResponseEntity<Success> registerDoctor(@RequestBody DoctorSignUpDTO doctorDto) {
        log.info("Doctor registration: {}", doctorDto);

        String doctorId = doctorService.signUpDoctor(doctorDto);

        Success successResponse = new Success();
        successResponse.setMessage("Doctor registered successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.CREATED);
        successResponse.setData(doctorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    // 🔹 User Registration
    @PostMapping("/user/register")
    public ResponseEntity<Success> registerUser(@RequestBody UserSignUpDTO userDto) {
        log.info("User registration: {}", userDto);

        String userId = userService.signUpUser(userDto);

        Success successResponse = new Success();
        successResponse.setMessage("User registered successfully");
        successResponse.setError(false);
        successResponse.setHttpStatus(HttpStatus.CREATED);
        successResponse.setData(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }


    // 🔹 Common Login Endpoint
    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<String>> registerEmployee(@RequestBody LoginRequest loginDTO) throws IOException {
        log.info("Application controller:registerTrainer execution start, {}", loginDTO);

        //check user & password

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        ApiResponse<String> response = ApiResponse.<String>builder()
                .message("User login successfully")
                .timestamp(LocalDateTime.now())
                .token(jwtUtils.generateToken(loginDTO.getUsername()))
                .data(null)
                .build();


        return ResponseEntity.ok(response);
    }
}
