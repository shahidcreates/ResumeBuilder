package com.shahidAnsari.ResumeBuilder.config;

import com.shahidAnsari.ResumeBuilder.entity.Role;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {

    private final UserRepository repo;
    private final PasswordEncoder encoder;


    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            if (repo.findByEmail("admin@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(
                        encoder.encode("admin123")
                );
                admin.setRole(Role.ADMIN);
                repo.save(admin);

            }
        };
    }

}