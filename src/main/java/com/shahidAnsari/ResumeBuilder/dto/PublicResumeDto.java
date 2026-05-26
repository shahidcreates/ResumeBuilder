package com.shahidAnsari.ResumeBuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicResumeDto {
    private Long id;
    private String title;
    private Long views;
    private Long downloads;
}
