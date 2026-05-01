package com.shahidAnsari.ResumeBuilder.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resume {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String thumbnailLink;

        @CreatedDate
        private LocalDateTime createdAt;

        @LastModifiedDate
        private LocalDateTime updatedAt;

        // 🔗 Many Resume → One User
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        // 🔗 One Resume → One PersonalDetails
        @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL)
        private PersonalDetails personalDetails;

        // 🔗 Many Resume → One Template
        @ManyToOne
        @JoinColumn(name = "template_id")
        private Template template;

        // 🔗 One Resume → Many Education
        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
        private List<Education> educations;

        // 🔗 One Resume → Many Experience
        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
        private List<Experience> experiences;

        // 🔗 One Resume → Many Skills
        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
        private List<Skills> skills;

        // 🔗 One Resume → Many Projects
        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
        private List<Projects> projects;

        // 🔗 One Resume → Many Certifications
        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
        private List<Certifications> certifications;

        // 🔗 One Resume → Many Languages
        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
        private List<Languages> languages;

}
