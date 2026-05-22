package com.shahidAnsari.ResumeBuilder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    private String profileImageUrl;
    private boolean emailVerified = false;
    private String verificationToken;
    private LocalDateTime verificationExpires;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}