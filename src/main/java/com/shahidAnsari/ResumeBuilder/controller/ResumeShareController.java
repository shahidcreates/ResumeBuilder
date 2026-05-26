package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.dto.ShareResponseDto;
import com.shahidAnsari.ResumeBuilder.service.ResumeShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/resumes")
@RequiredArgsConstructor
public class ResumeShareController {
    private final ResumeShareService resumeShareService;

    @PostMapping("/{resumeId}/share")
    public ShareResponseDto shareResume(@PathVariable Long resumeId){
        return resumeShareService.generateShareLink(resumeId);
    }
}
