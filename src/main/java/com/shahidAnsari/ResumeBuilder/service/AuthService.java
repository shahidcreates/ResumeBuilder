package com.shahidAnsari.ResumeBuilder.service;

import com.shahidAnsari.ResumeBuilder.dto.AuthResponse;
import com.shahidAnsari.ResumeBuilder.dto.LoginRequest;
import com.shahidAnsari.ResumeBuilder.dto.RegisterRequest;
import com.shahidAnsari.ResumeBuilder.entity.Role;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.exception.ResourceExistsExcepton;
import com.shahidAnsari.ResumeBuilder.repository.UserRepository;
import com.shahidAnsari.ResumeBuilder.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${app.base.url:http://localhost:8080}")
    private String appBaseUrl;

    public AuthResponse register(RegisterRequest request){
        log.info("Inside AuthService : register() {}",request);

        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceExistsExcepton("User already exists with this email");
        }

        User newUser = toUserEntity(request);

        newUser.setRole(Role.USER);
        userRepository.save(newUser);
        sendVerificationEmail(newUser);
        return toResponse(newUser);

    }

    private void sendVerificationEmail(User newUser) {
        log.info("Inside AuthService - sendVerificationEmail(): {}",newUser);
        try{
            String link= appBaseUrl+"/api/auth/verify-email?token="+newUser.getVerificationToken();
            String html = "<div style='font-family:sans-serif'"+
                                "<h2>Verify your email</h2>" +
                                "<p>Hi "+newUser.getName()+" ,please conform your email to activate your account. </p>"+
                                "<p><a href='"+link+
                                "' style='display:inline-block;padding:10px 16px;background:#6366F1;color:#fff;border-radius:6px;text-decoration:none'>Verify Email</a></p>"+
                                "<p>Or copy this link: "+link+" </p>"+
                                "<p>This link expires in 24 hours.</p>"+
                            "</div>";
            emailService.sendHtmlEmail(newUser.getEmail(), "Verify your email",html);
        } catch (Exception e) {
            log.error("Exception occurred at sendVerificationEmail(): {}",e.getMessage());
            throw new RuntimeException("Failed to send verification email : "+e.getMessage());
        }
    }

    //  Entity to Dto
    private AuthResponse toResponse(User newUser){
        AuthResponse response = new AuthResponse();
        response.setId(newUser.getId());
        response.setName(newUser.getName());
        response.setEmail(newUser.getEmail());
        response.setProfileImageUrl(newUser.getProfileImageUrl());
        response.setEmailVarified(newUser.isEmailVerified());
        response.setCreatedAt(newUser.getCreatedAt());
        response.setUpdatedAt(newUser.getUpdatedAt());
        return response;

    }
    //  Dto to Entity
    private User toUserEntity(RegisterRequest request){
          User user = new User();
          user.setName(request.getName());
          user.setEmail(request.getEmail());
          user.setPassword(passwordEncoder.encode(request.getPassword()));
          user.setProfileImageUrl(request.getProfileImageUrl());
          user.setEmailVerified(false);
          user.setVerificationToken(UUID.randomUUID().toString());
          user.setVerificationExpires(LocalDateTime.now().plusHours(24));
          user.setCreatedAt(LocalDateTime.now());
          user.setUpdatedAt(LocalDateTime.now());
          return user;
    }

    public void verifyEmail(String token){
        log.info("Inside AuthService: verifyEmail(): {}",token);
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired verification token "));
        if(user.getVerificationExpires() != null && user.getVerificationExpires().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Verification token expired. Please request new one.");
        }
        user.setEmailVerified(true);
        user.setVerificationExpires(null);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request){

        User existingUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("Invalid email"));

        if(!passwordEncoder.matches(request.getPassword(),existingUser.getPassword())){
            throw new UsernameNotFoundException("Invalid password");
        }

        if(!existingUser.isEmailVerified()){
            throw new RuntimeException("Please verify your email before logging in.");
        }

        if(!existingUser.isEnabled()){
            throw new RuntimeException("You are block by admin");
        }

        String token = jwtUtil.generateToken(String.valueOf(existingUser.getId()));

        AuthResponse response = toResponse(existingUser);
        response.setToken(token);
        return response;
    }


    public void resendVerification(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));

        if(user.isEmailVerified()){
            throw new RuntimeException("Email is  already verified");
        }

        user.setVerificationToken(UUID.randomUUID().toString());
        user.setVerificationExpires(LocalDateTime.now().plusHours(24));

        userRepository.save(user);

        sendVerificationEmail(user);
    }

    public AuthResponse getProfile(Object principalObject) {

        User existingUser =(User) principalObject;
        return toResponse(existingUser);
    }
}
