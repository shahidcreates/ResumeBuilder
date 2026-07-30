package com.shahidAnsari.ResumeBuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shahidAnsari.ResumeBuilder.entity.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicResumeDto {

    private Long id;
    private String title;
    private String templateName;
    private String thumbnailLink;
    private String summary;

    private PersonalDetails personalDetails;

    private List<Education> educations;
    private List<Experience> experiences;
    private Skills skills;
    private List<Projects> projects;
    private List<Certifications> certifications;
    private List<Languages> languages;


    private Long views;
    private Long downloads;
}
