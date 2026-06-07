package com.shahidAnsari.ResumeBuilder.controller;
import com.shahidAnsari.ResumeBuilder.dto.ResumeRequestDto;
import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.service.FileUploadService;
import com.shahidAnsari.ResumeBuilder.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.shahidAnsari.ResumeBuilder.util.AppConstants.*;
@Tag(
        name = "Resume REST API CRUD operation",
        description = "CREATE, READ, UPDATE and DELETE operation for Resume REST API"
)
@RestController
@RequestMapping(RESUMES)
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;
    private final FileUploadService fileUploadService;


    @Operation(
            summary = "Create resume",
            description = "REST API to create resumes"
    )
    @PostMapping
    public ResponseEntity<?> createResume(@Valid @RequestBody ResumeRequestDto request, Authentication authentication){

        Resume newResume = resumeService.createResume(request,authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.CREATED).body(newResume);
    }


    @Operation(
            summary = "Fetch all resumes",
            description = "REST API to fetch all resumes for user"
    )
    @GetMapping
    public ResponseEntity<?> getUserResumes(Authentication authentication){
        List<Resume> resumes = resumeService.getUserResumes(authentication.getPrincipal());
        return ResponseEntity.ok(resumes);
    }


    @Operation(
            summary = "Fetch resume by ResumeId",
            description = "REST API to fetch resume by ResumeId"
    )
    @GetMapping(ID)
    public ResponseEntity<?> getResumeById(@PathVariable Long id, Authentication authentication){
        Resume existingResume = resumeService.getResumeById(id,authentication.getPrincipal());
        return ResponseEntity.ok(existingResume);
    }


    @Operation(
            summary = "Update resume by ResumeId",
            description = "REST API to update resume by ResumeId"
    )
    @PutMapping(ID)
    public ResponseEntity<?> updateResumeById(@PathVariable Long id, @RequestBody Resume updatedData, Authentication authentication){
        Resume updatedResume = resumeService.updateResume(id,updatedData,authentication.getPrincipal());
        return ResponseEntity.ok(updatedResume);
    }


    @Operation(
            summary = "Upload resume images",
            description = "REST API to upload resume images"
    )
    @PutMapping(UPLOAD_RESUME_IMAGE)
    public ResponseEntity<?> uploadResumeImages(@PathVariable Long id,
                                                @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                                @RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
                                                HttpServletRequest request, Authentication authentication) throws IOException {

        Map<String, String> response = fileUploadService.uploadResumeImages(id,authentication.getPrincipal(),thumbnail,profileImage);

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Delete resume by ResumeId",
            description = "REST API to Delete resume by ResumeId"
    )
    @DeleteMapping(ID)
    public ResponseEntity<?> deleteResume(@PathVariable Long id, Authentication authentication){
        resumeService.deleteResume(id,authentication.getPrincipal());
        return ResponseEntity.ok(Map.of("message","Resume deleted successfully"));
    }

}
