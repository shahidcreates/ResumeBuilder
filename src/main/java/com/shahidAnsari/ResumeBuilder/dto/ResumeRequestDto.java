package com.shahidAnsari.ResumeBuilder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(
        name = "Resume Request",
        description = "It holds the resume request info"
)
@Data
public class ResumeRequestDto {
    @NotBlank(message = "title is required")
    private String title;
}
