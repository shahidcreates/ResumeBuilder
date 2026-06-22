package com.shahidAnsari.ResumeBuilder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String templateName;
    private String thumbnailLink;
    private String summary;

    @Column(unique = true)
    private String shareToken;
    private boolean isPublic = false;

    @Column(unique = true)
    private String shareSlug;

    @Column(nullable = false)
    private Long views = 0L;
    @Column(nullable = false)
    private Long downloads = 0L;

    private LocalDateTime sharedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private PersonalDetails personalDetails;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Education> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Experience> experiences;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Skills skills;   // List<Skills> ko Skills me change kiya

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Projects> projects;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Certifications> certifications;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Languages> languages;
}