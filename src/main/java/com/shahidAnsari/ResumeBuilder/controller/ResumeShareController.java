package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.dto.PublicResumeDto;
import com.shahidAnsari.ResumeBuilder.dto.ShareResponseDto;
import com.shahidAnsari.ResumeBuilder.service.ResumeShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResumeShareController {
    private final ResumeShareService resumeShareService;

    // ====================================
    // Share Resume
    // ====================================

    @PostMapping("/resumes/{resumeId}/share")
    public ShareResponseDto shareResume(@PathVariable Long resumeId) {
        return resumeShareService.generateShareLink(resumeId);
    }

    // ====================================
    // Public Resume
    // ====================================

    @GetMapping("/public/resumes/{slug}/{token}")
    public PublicResumeDto getPublicResume(@PathVariable String slug, @PathVariable String token) {
        return resumeShareService.getPublicResume(slug,token);
    }

    // ====================================
    // Disable Sharing
    // ====================================

    @PatchMapping("/resumes/{resumeId}/disable-share")
    public String disableShare(@PathVariable Long resumeId) {
        return resumeShareService.disableSharing(resumeId);
    }

    // ====================================
    // Regenerate Link
    // ====================================

    @PatchMapping("/resumes/{resumeId}/regenerate-link")
    public ShareResponseDto regenerateLink(@PathVariable Long resumeId) {
        return resumeShareService.regenerateLink(resumeId);
    }

    // ====================================
    // Increase Download Count
    // ====================================

    @PatchMapping("/public/resumes/{slug}/download")
    public String increaseDownload(@PathVariable String slug) {
        resumeShareService.increaseDownloadCount(slug);
        return "Download count updated";
    }
}
