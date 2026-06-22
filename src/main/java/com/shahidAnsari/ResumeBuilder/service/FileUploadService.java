package com.shahidAnsari.ResumeBuilder.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shahidAnsari.ResumeBuilder.dto.AuthResponse;
import com.shahidAnsari.ResumeBuilder.entity.PersonalDetails;
import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {
    private final Cloudinary cloudinary;
    private final AuthService authService;
    private final ResumeRepository resumeRepository;


    public Map<String,String> uploadSingleImage(MultipartFile file) throws IOException {
        Map<String, Object>  imageUploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","image"));
        log.info("Inside FileUploadService - UploadSingleImage(): {}",imageUploadResult.get("secure_url").toString());

        return Map.of("imageUrl",imageUploadResult.get("secure_url").toString());
    }

    public Map<String, String> uploadResumeImages(Long resumeId, @Nullable Object principal, MultipartFile thumbnail, MultipartFile profileImage) throws IOException {
        //1. Get the current profile
        AuthResponse response = authService.getProfile(principal);

        //2. Get the existing resume
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(),resumeId).
                orElseThrow(()-> new RuntimeException("Resume not found"));

        //3. Upload the resume images and set the resume
        Map<String,String> returnValue = new HashMap<>();
        Map<String,String> uploadResult;

        if(Objects.nonNull(thumbnail)){
            uploadResult = uploadSingleImage(thumbnail);
            log.info("Inside FileUploadService - UploadResumeImage(): {}",uploadResult.get("imageUrl").toString());

            existingResume.setThumbnailLink(uploadResult.get("imageUrl"));
            returnValue.put("thumbnailLink",uploadResult.get("imageUrl"));
        }
        if(Objects.nonNull(profileImage)){
            uploadResult = uploadSingleImage(profileImage);
            if(Objects.isNull(existingResume.getPersonalDetails())){
                existingResume.setPersonalDetails(new PersonalDetails());
            }
            existingResume.getPersonalDetails().setProfilePreviewUrl(uploadResult.get("imageUrl"));
            returnValue.put("profilePreviewUrl",uploadResult.get("imageUrl"));
        }

        //4. update the details into database
        resumeRepository.save(existingResume);
        returnValue.put("message","Images uploaded successfully");

        //5. return the result
        return returnValue;

    }
}
