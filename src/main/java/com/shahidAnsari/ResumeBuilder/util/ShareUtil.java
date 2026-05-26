package com.shahidAnsari.ResumeBuilder.util;


import java.security.SecureRandom;

public class ShareUtil {
    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public static String generateToken(int length){
        StringBuilder sb = new StringBuilder();
        for(int i=0 ; i<length; i++){
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }

    public static String generateSlug(String title){
        return title.toLowerCase()
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-+","-");
    }
}
