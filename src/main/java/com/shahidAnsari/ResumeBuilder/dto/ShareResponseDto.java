package com.shahidAnsari.ResumeBuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShareResponseDto {
    private String shareUrl;
    private String token;
    private String slug;
}
