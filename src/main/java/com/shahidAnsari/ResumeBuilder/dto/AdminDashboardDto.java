package com.shahidAnsari.ResumeBuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminDashboardDto {

    private Long totalUsers;
    private Long totalResumes;
    private Long admins;
    private Long normalUsers;
}
