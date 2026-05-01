package com.shahidAnsari.ResumeBuilder.service;

import com.shahidAnsari.ResumeBuilder.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplatesService {
    private final AuthService authService;

    public List<String> getTemplates(Object principal){
        AuthResponse authResponse = authService.getProfile(principal);

        return List.of("01","02","03");
    }
}
