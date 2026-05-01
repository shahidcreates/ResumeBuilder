package com.shahidAnsari.ResumeBuilder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //profile image
    private String profilePreviewUrl;
    private String fullName;
    private String phone;
    private String address;
    private String linkedin;
    private String github;
    private String website;

    @OneToOne
    @JoinColumn(name = "resume_id")
    @JsonBackReference
    private Resume resume;
}
