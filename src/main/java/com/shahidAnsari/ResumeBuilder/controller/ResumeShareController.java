package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.dto.PublicResumeDto;
import com.shahidAnsari.ResumeBuilder.dto.ShareResponseDto;
import com.shahidAnsari.ResumeBuilder.service.ResumeShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Resume Sharing APIs",
        description = "REST APIs for sharing resumes publicly and managing share links"
)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResumeShareController {
    private final ResumeShareService resumeShareService;

    // ====================================
    // Share Resume
    // ====================================

    @Operation(
            summary = "Generate Resume Share Link",
            description = "Generate a public shareable link for a specific resume"
    )
    @PostMapping("/resumes/{resumeId}/share")
    public ShareResponseDto shareResume(
            @Parameter(description = "Resume ID", example = "1")
            @PathVariable Long resumeId) {

        return resumeShareService.generateShareLink(resumeId);
    }


    // ====================================
    // Public Resume
    // ====================================

    @Operation(
            summary = "Get Public Resume",
            description = "Fetch a publicly shared resume using slug and access token"
    )
    @GetMapping("/public/resumes/{slug}/{token}")
    public PublicResumeDto getPublicResume(
            @Parameter(description = "Resume slug", example = "java-fullstack-developer")
            @PathVariable String slug,

            @Parameter(description = "Resume access token")
            @PathVariable String token) {

        return resumeShareService.getPublicResume(slug, token);
    }


    // ====================================
    // Disable Sharing
    // ====================================

    @Operation(
            summary = "Disable Resume Sharing",
            description = "Disable public sharing for a specific resume"
    )
    @PatchMapping("/resumes/{resumeId}/disable-share")
    public String disableShare(
            @Parameter(description = "Resume ID", example = "1")
            @PathVariable Long resumeId) {

        return resumeShareService.disableSharing(resumeId);
    }

    // ====================================
    // Regenerate Link
    // ====================================

    @Operation(
            summary = "Regenerate Share Link",
            description = "Generate a new share link and invalidate the previous one"
    )
    @PatchMapping("/resumes/{resumeId}/regenerate-link")
    public ShareResponseDto regenerateLink(
            @Parameter(description = "Resume ID", example = "1")
            @PathVariable Long resumeId) {

        return resumeShareService.regenerateLink(resumeId);
    }


    // ====================================
    // Increase Download Count
    // ====================================

    @Operation(
            summary = "Increase Download Count",
            description = "Increment the download counter when a public resume is downloaded"
    )
    @PatchMapping("/public/resumes/{slug}/download")
    public String increaseDownload(
            @Parameter(description = "Resume slug", example = "java-fullstack-developer")
            @PathVariable String slug) {

        resumeShareService.increaseDownloadCount(slug);
        return "Download count updated";
    }

}
