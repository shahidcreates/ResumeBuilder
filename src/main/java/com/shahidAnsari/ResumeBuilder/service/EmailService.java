package com.shahidAnsari.ResumeBuilder.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import org.springframework.http.*;






@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

//    private final JavaMailSender mailSender;
//
//    public void sendHtmlEmail(String to,String subject,String htmlContent) throws MessagingException {
//        log.info("Inside EmailService - sendHtmlEmail(): {} {} {}",to,subject,htmlContent);
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
//        helper.setFrom(fromEmail);
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlContent,true);
//        mailSender.send(message);
//    }

    private final RestTemplate restTemplate;

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    public void sendHtmlEmail(
            String to,
            String subject,
            String htmlContent) {

        String url = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> requestBody = Map.of(
                "sender", Map.of(
                        "name", "Resumify",
                        "email", senderEmail
                ),
                "to", List.of(
                        Map.of("email", to)
                ),
                "subject", subject,
                "htmlContent", htmlContent
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        String.class
                );

        log.info("Brevo Response: {}", response.getBody());
    }
}
