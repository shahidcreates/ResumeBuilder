package com.shahidAnsari.ResumeBuilder.service;


import com.shahidAnsari.ResumeBuilder.dto.ShareResponseDto;
import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeShareService {
    private final ResumeRepository resumeRepository;

    public ShareResponseDto generateShareLink(Long resumeId){
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(()->new RuntimeException("Resume not found"));

        String token = UUID.randomUUID().toString();

        resume.setShareToken(token);
        resume.setPublic(true);

        resumeRepository.save(resume);

        String shareLink =
                "http://localhost:8080/api/public/resumes/" + token;
        return new ShareResponseDto(shareLink);
    }

    public Resume getPublicResume(String token){
        Resume resume = resumeRepository.findByShareToken(token)
                .orElseThrow(()-> new RuntimeException("Invalid share link"));

        if(!resume.isPublic()){
            throw new RuntimeException("Resume is private");
        }

        return resume;
    }
}
