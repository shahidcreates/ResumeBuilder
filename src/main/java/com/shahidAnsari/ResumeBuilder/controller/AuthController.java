package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.dto.AuthResponse;
import com.shahidAnsari.ResumeBuilder.dto.LoginRequest;
import com.shahidAnsari.ResumeBuilder.dto.RegisterRequest;
import com.shahidAnsari.ResumeBuilder.dto.ResetPasswordRequest;
import com.shahidAnsari.ResumeBuilder.service.AuthService;
import com.shahidAnsari.ResumeBuilder.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static com.shahidAnsari.ResumeBuilder.util.AppConstants.*;

@Tag(
        name = "User Authentication REST API"
)
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(AUTH_CONTROLLER)
public class AuthController {

    private final AuthService authService;
    private final FileUploadService fileUploadService;

    @Operation(
            summary = "For User Registration",
            description = "REST API for user registration"
    )
    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        log.info("Inside AuthController - register(): {}",request);
        AuthResponse response = authService.register(request);
        log.info("Response from service : {}",response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "For User Login",
            description = "REST API for user login"
    )
    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "REST API for Email Verification",
            description = "API for email verify"
    )
    @GetMapping(VERIFY_EMAIL) // later after frontend user direct to Login Page.
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        log.info("Inside AuthController - verifyEmail(): {}",token);
        authService.verifyEmail(token);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","Email verified successfully"));
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "https://resumify-project-1.vercel.app/auth")
                .build();
    }

    // sent otp
    @Operation(
            summary = "Send Password Reset OTP",
            description = "Enter the registered email address. A 6-digit OTP will be sent to that email."
    )
    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@Parameter(description = "Registered user email address", example = "shahid.ans3001@gmail.com")
                                                     @RequestParam String email) {
            log.info("Inside AuthController - forgotPassword(): {}",email);
            authService.forgotPassword(email);
            return ResponseEntity.ok("OTP sent successfully");
    }

    // Verify OTP + Reset Password
    @Operation(
            summary = "Reset Password",
            description = "Verify OTP and set a new password."
    )
    @PostMapping(RESET_PASSWORD)
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        log.info("Inside AuthController - resetPassword(): ");
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }

    @Operation(
            summary = "REST API for Upload image",
            description = "API for upload user image"
    )
    @PostMapping(UPLOAD_PROFILE)
    public ResponseEntity<?> uploadImage(@RequestPart("image")MultipartFile file) throws IOException {
        log.info("Inside AuthController - uploadImage(): ");

        Map<String,String> response=fileUploadService.uploadSingleImage(file);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "REST API for Resend email Verification",
            description = "API for resend the verify email"
    )
    @PostMapping(RESEND_VERIFICATION)
    public ResponseEntity<?> resendVerification(@RequestBody Map<String,String> body){
        String email = body.get("email");
        if(Objects.isNull(email)){
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }
        authService.resendVerification(email);
        return ResponseEntity.ok(Map.of("success",true,"message","Verification email send"));
    }

    @Operation(
            summary = "Fetch REST API for Profile",
            description = "API for fetch user profile"
    )
    @GetMapping(PROFILE)
    public ResponseEntity<?> getProfile(Authentication authentication){
       Object principalObject =  authentication.getPrincipal();

       AuthResponse currentProfile = authService.getProfile(principalObject);

       return ResponseEntity.ok(currentProfile);

    }
}
