package com.shahidAnsari.ResumeBuilder.service;


import com.shahidAnsari.ResumeBuilder.dto.PublicResumeDto;
import com.shahidAnsari.ResumeBuilder.dto.ShareResponseDto;
import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.repository.ResumeRepository;
import com.shahidAnsari.ResumeBuilder.util.ShareUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeShareService {
    private final ResumeRepository resumeRepository;

    private static final String BASE_URL=
            "http://localhost:8080/api/public/resumes/";

    // Generate Share Link
    public ShareResponseDto generateShareLink(Long resumeId){
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(()->new RuntimeException("Resume not found"));

        String token = ShareUtil.generateToken(10);
        String baseSlug = ShareUtil.generateSlug(resume.getTitle());

        String slug = baseSlug;
        int count =1;
        while(resumeRepository.existsByShareSlug(slug)){
            slug = baseSlug + "-"+count;
            count++;
        }

        resume.setShareToken(token);
        resume.setShareSlug(slug);
        resume.setPublic(true);
        resume.setSharedAt(LocalDateTime.now());

        resumeRepository.save(resume);

        return ShareResponseDto.builder()
                .shareUrl(BASE_URL+slug+"/"+token)
                .token(token)
                .slug(slug)
                .build();
    }

    //    Get Public Resume
    public PublicResumeDto getPublicResume(String slug,String token){
        Resume resume= resumeRepository.findByShareSlug(slug)
                .orElseThrow(()-> new RuntimeException("Invalid link"));

        if(!resume.isPublic()){
            throw new RuntimeException("Resume private");
        }

        if(!resume.getShareToken().equals(token)){
            throw new RuntimeException("Invalid Token");
        }

        long currentViews = resume.getViews() == null ? 0L : resume.getViews();
        resume.setViews(currentViews + 1);
        resumeRepository.save(resume);
        return mapToDto(resume);
    }

    // Disable Sharing
    public String disableSharing(Long resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        resume.setPublic(false);
        resume.setShareToken(null);
        resume.setShareSlug(null);

        resumeRepository.save(resume);

        return "Sharing disabled successfully";
    }


    // Regenerate Share Link
    public ShareResponseDto regenerateLink(Long resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        // generate token
        String newToken = ShareUtil.generateToken(10);

        // generate slug if null
        String slug = resume.getShareSlug();
        if (slug == null || slug.isBlank()) {

            String baseSlug = ShareUtil.generateSlug(resume.getTitle());

            slug = baseSlug;

            int count = 1;

            while (resumeRepository.existsByShareSlug(slug)) {

                slug = baseSlug + "-" + count;
                count++;
            }
        }
        resume.setShareToken(newToken);
        resume.setShareSlug(slug);
        resume.setPublic(true);

        resumeRepository.save(resume);

        return ShareResponseDto.builder()
                .shareUrl(BASE_URL + resume.getShareSlug())
                .token(newToken)
                .slug(resume.getShareSlug())
                .build();
    }

    // Download Counter
    public void increaseDownloadCount(String slug) {

        Resume resume = resumeRepository
                .findByShareSlug(slug)
                .orElseThrow(() ->
                        new RuntimeException("Resume not found"));

        long currentDownloads = resume.getDownloads() == null ? 0L : resume.getDownloads();

        resume.setDownloads(currentDownloads + 1);
        resumeRepository.save(resume);
    }

    // DTO Mapper
    private PublicResumeDto mapToDto(Resume resume) {

        return PublicResumeDto.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .views(resume.getViews())
                .downloads(resume.getDownloads())
                .build();
    }
}
