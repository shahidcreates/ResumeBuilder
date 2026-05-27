package com.shahidAnsari.ResumeBuilder.service;

import com.shahidAnsari.ResumeBuilder.dto.AdminDashboardDto;
import com.shahidAnsari.ResumeBuilder.dto.UserResumeStatsDto;
import com.shahidAnsari.ResumeBuilder.entity.Resume;
import com.shahidAnsari.ResumeBuilder.entity.Role;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.repository.ResumeRepository;
import com.shahidAnsari.ResumeBuilder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final AuthService authService;

    //get Users
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new RuntimeException("No users are found");
        }
        return users;
    }

    //get User By Id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("User not found"));
    }

    //Delete User By Id
    public String deleteUser(Long id) {

        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not exists");
        }
        userRepository.deleteById(id);
        return "User deleted";
    }

    //Update User Role
    public String updateUserRole(Long id, Map<String,String> body) {
        User user = getUserById(id);
        user.setRole(Role.valueOf(body.get("role")));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "Role Updated";
    }

    //Disable User
    public String disableUser(Long id) {
        User user = getUserById(id);
        user.setEnabled(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User disable";
    }

    //Enable User
    public String enableUser(Long id) {
        User user = getUserById(id);
        user.setEnabled(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "User enable";
    }

    // get State
    public Map<String,Long> stats(){

        Map<String,Long> data = new HashMap<>();

        data.put("totalUsers",userRepository.count());
        data.put("admins", userRepository.countByRole(Role.ADMIN));
        data.put("normalUsers", userRepository.countByRole(Role.USER));

        return data;
    }

    //get all resume
    public List<Resume> getResumes() {
        List<Resume> resumes = resumeRepository.findAll();
        if(resumes.isEmpty()){
            throw new RuntimeException("No resumes found");
        }
        return resumes;
    }

    // DASHBOARD
    public AdminDashboardDto dashboard() {

        return new AdminDashboardDto(
                userRepository.count(),
                resumeRepository.count(),
                userRepository.countByRole(Role.ADMIN),
                userRepository.countByRole(Role.USER)
        );
    }

    // Count resume by user
    public UserResumeStatsDto getUserResumeCount(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));

        long resumeCount = resumeRepository.countByUserId(userId);

        return new UserResumeStatsDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                resumeCount
        );
    }

    // get all user resume stats
    public List<UserResumeStatsDto> getUserResumeStats() {

        List<Object[]> rows = userRepository.getUserResumeStats();

        List<UserResumeStatsDto> response = new ArrayList<>();

        for (Object[] row : rows) {

            response.add(
                    new UserResumeStatsDto(
                            ((Number) row[0]).longValue(),
                            (String) row[1],
                            (String) row[2],
                            ((Number) row[3]).longValue()
                    )
            );
        }

        return response;
    }
}
