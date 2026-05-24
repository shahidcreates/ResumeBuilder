package com.shahidAnsari.ResumeBuilder.util;

public class AppConstants {
    public static final String AUTH_CONTROLLER ="/api/auth";
    public static final String REGISTER ="/register";
    public static final String VERIFY_EMAIL ="/verify-email";
    public static final String UPLOAD_PROFILE ="/upload-image";
    public static final String LOGIN ="/login";
    public static final String RESEND_VERIFICATION="/resend-verification";
    public static final String PROFILE="/profile";


    public static final String RESUMES="/api/resumes";
    public static final String ID="/{id}";
    public static final String UPLOAD_RESUME_IMAGE="/{id}/upload-images";


    public static final String TEMPLATES="/api/templates";

    public static final String ADMIN ="/api/admin";
    public static final String ALL_USERS ="/users";
    public static final String USER_BY_ID ="/users/{id}";
    public static final String USER_ROLE ="/users/{id}/role";
    public static final String USER_ENABLE ="/users/{id}/enable";
    public static final String USER_DISABLE ="/users/{id}/disable";
    public static final String STATS ="/stats";
    public static final String ALL_RESUMES ="/resumes";
    public static final String DASHBOARD ="/dashboard";
    public static final String USER_RESUMES ="/users/{userId}/resume-count";
    public static final String USER_RESUMES_STATS ="/users/resume-stats";
}
