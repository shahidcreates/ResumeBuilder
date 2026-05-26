package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.service.ResumeShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/resumes")
@RequiredArgsConstructor
public class PublicResumeController {
    private final ResumeShareService resumeShareService;

    @GetMapping("/{token}")
    public Resume getPublicResume(@PathVariable String token){
        return resumeShareService.getPublicResume(token);
    }
}
