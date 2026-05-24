package com.shahidAnsari.ResumeBuilder.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResumeStatsDto {
    private Long userId;
    private String name;
    private String email;
    private Long resumeCount;
}
