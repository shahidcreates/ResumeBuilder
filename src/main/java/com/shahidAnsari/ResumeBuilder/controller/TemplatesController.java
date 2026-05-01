package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.service.TemplatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.shahidAnsari.ResumeBuilder.util.AppConstants.TEMPLATES;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(TEMPLATES)
public class TemplatesController {

    private final TemplatesService templatesService;

    @GetMapping
    public ResponseEntity<?> getTemplates(Authentication authentication){
        List<String> response = templatesService.getTemplates(authentication.getPrincipal());
        return ResponseEntity.ok(response);
    }
}
