package com.shahidAnsari.ResumeBuilder.service;

import com.shahidAnsari.ResumeBuilder.dto.AuthResponse;
import com.shahidAnsari.ResumeBuilder.dto.ResumeRequestDto;
import com.shahidAnsari.ResumeBuilder.entity.PersonalDetails;
import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.repository.ResumeRepository;
import com.shahidAnsari.ResumeBuilder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public Resume createResume(ResumeRequestDto request, Object principalObject) {
        // 1. Create resume object
        Resume newResume = new Resume();

        // 2. Get the current profile
        AuthResponse response = authService.getProfile(principalObject);

        // 3. Fetch User from DB
        User user = userRepository.findById(response.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4. Set data
        // update the resume object
        newResume.setUser(user);
        newResume.setTitle(request.getTitle());

        // set default data for resume
        setDefaultResumeData(newResume);

        // save the resume data
         return resumeRepository.save(newResume);
    }

    private void setDefaultResumeData(Resume newResume) {
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setResume(newResume);

        newResume.setPersonalDetails(personalDetails);
        newResume.setEducations(new ArrayList<>());
        newResume.setExperiences(new ArrayList<>());
        newResume.setSkills(new ArrayList<>());
        newResume.setLanguages(new ArrayList<>());
        newResume.setCertifications(new ArrayList<>());
        newResume.setProjects(new ArrayList<>());

    }

    public List<Resume> getUserResumes(Object principal) {
        //1. Get the current profile
        AuthResponse response = authService.getProfile(principal);

        //2. Call the repository finder method
        List<Resume> resumes = resumeRepository.findByUserIdOrderByUpdatedAtDesc(response.getId());

        //3. return the response
        return resumes;

    }

    public Resume getResumeById(Long resumeId, Object principal) {
        //1. Get the current profile
        AuthResponse response = authService.getProfile(principal);

        //2. Call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(),resumeId).
                orElseThrow(()->new RuntimeException("Resume not found"));
        //3. return the response
        return existingResume;
    }

    public Resume updateResume(Long resumeId, Resume updatedData, @Nullable Object principal) {
        //1. Get the current profile
        AuthResponse response = authService.getProfile(principal);

        //2. Call the repository finder method
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(),resumeId).
                orElseThrow(()->new RuntimeException("Resume not found"));

        //3. update the new data
//        existingResume.setTitle(updatedData.getTitle());
//        existingResume.setPersonalDetails(updatedData.getPersonalDetails());
//        existingResume.setExperiences(updatedData.getExperiences());
//        existingResume.setEducations(updatedData.getEducations());
//        existingResume.setProjects(updatedData.getProjects());
//        existingResume.setCertifications(updatedData.getCertifications());
//        existingResume.setSkills(updatedData.getSkills());
//        existingResume.setLanguages(updatedData.getLanguages());
//        existingResume.setUpdatedAt(LocalDateTime.now());

        // 3. Update basic fields
        existingResume.setTitle(updatedData.getTitle());
        existingResume.setUpdatedAt(LocalDateTime.now());

        // =========================
        // Personal Details
        // =========================
        if (updatedData.getPersonalDetails() != null) {
            updatedData.getPersonalDetails().setResume(existingResume);
            existingResume.setPersonalDetails(updatedData.getPersonalDetails());
        }

        // =========================
        // Educations
        // =========================
        if (updatedData.getEducations() != null) {
            updatedData.getEducations().forEach(education ->
                    education.setResume(existingResume));

            existingResume.setEducations(updatedData.getEducations());
        }

        // =========================
        // Experiences
        // =========================
        if (updatedData.getExperiences() != null) {
            updatedData.getExperiences().forEach(experience ->
                    experience.setResume(existingResume));

            existingResume.setExperiences(updatedData.getExperiences());
        }

        // =========================
        // Skills
        // =========================
        if (updatedData.getSkills() != null) {
            updatedData.getSkills().forEach(skill ->
                    skill.setResume(existingResume));

            existingResume.setSkills(updatedData.getSkills());
        }

        // =========================
        // Projects
        // =========================
        if (updatedData.getProjects() != null) {
            updatedData.getProjects().forEach(project ->
                    project.setResume(existingResume));

            existingResume.setProjects(updatedData.getProjects());
        }

        // =========================
        // Certifications
        // =========================
        if (updatedData.getCertifications() != null) {
            updatedData.getCertifications().forEach(certification ->
                    certification.setResume(existingResume));

            existingResume.setCertifications(updatedData.getCertifications());
        }

        // =========================
        // Languages
        // =========================
        if (updatedData.getLanguages() != null) {
            updatedData.getLanguages().forEach(language ->
                    language.setResume(existingResume));

            existingResume.setLanguages(updatedData.getLanguages());
        }

        //4. save the detail
        resumeRepository.save(existingResume);
        return existingResume;
    }

    public void deleteResume(Long resumeId,  Object principal) {
        AuthResponse response = authService.getProfile(principal);
        Resume existingResume = resumeRepository.findByUserIdAndId(response.getId(),resumeId)
                .orElseThrow(()-> new RuntimeException("Resume not found"));
        resumeRepository.delete(existingResume);
    }
}
