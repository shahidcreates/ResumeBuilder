package com.shahidAnsari.ResumeBuilder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//AuthDto
@Schema(
        name = "Response",
        description = "It holds response information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    long id;
    private String name;
    private String email;
    private String profileImageUrl;
    private boolean emailVarified;
    private String token;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
