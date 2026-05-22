package com.shahidAnsari.ResumeBuilder.controller;

import com.shahidAnsari.ResumeBuilder.entity.Role;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){

        userRepository.deleteById(id);

        return "User Deleted";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id){

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/users/{id}/role")
    public String updateRole(@PathVariable Long id, @RequestBody Map<String,String> body){

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        user.setRole(
                Role.valueOf(body.get("role"))
        );

        userRepository.save(user);

        return "Role Updated";
    }

    @PutMapping("/users/{id}/disable")
    public String disableUser(
            @PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        user.setEnabled(false);

        userRepository.save(user);

        return "User Disabled";
    }

    @PutMapping("/users/{id}/enable")
    public String enableUser(
            @PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        user.setEnabled(true);

        userRepository.save(user);

        return "User Enabled";
    }

    @GetMapping("/stats")
    public Map<String,Long> stats(){

        Map<String,Long> data = new HashMap<>();

        data.put(
                "totalUsers",
                userRepository.count()
        );

        data.put(
                "admins",
                userRepository.countByRole(Role.ADMIN)
        );

        data.put(
                "normalUsers",
                userRepository.countByRole(Role.USER)
        );

        return data;
    }
}