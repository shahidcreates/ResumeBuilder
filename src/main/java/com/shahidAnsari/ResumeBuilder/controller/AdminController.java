package com.shahidAnsari.ResumeBuilder.controller;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import static com.shahidAnsari.ResumeBuilder.util.AppConstants.*;

@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // get all user
    @GetMapping(ALL_USERS)
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // delete user by id
    @DeleteMapping(USER_BY_ID)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }

    // get user by id
    @GetMapping(USER_BY_ID)
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    // change user Role
    @PutMapping(USER_ROLE)
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Map<String,String> body) {
        return ResponseEntity.ok(adminService.updateUserRole(id, body));
    }

    // Block user
    @PutMapping(USER_DISABLE)
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.disableUser(id));
    }

    // Unblock user
    @PutMapping(USER_ENABLE)
    public ResponseEntity<?> enableUser(@PathVariable Long id){
        return ResponseEntity.ok(adminService.enableUser(id));
    }

    // STATS
    @GetMapping(STATS)
    public ResponseEntity<?> stats() {
        return ResponseEntity.ok(adminService.stats());
    }

    // GET RESUMES
    @GetMapping("/resumes")
    public ResponseEntity<?> resumes() {
        return ResponseEntity.ok(adminService.getResumes());
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        return ResponseEntity.ok(adminService.dashboard());
    }

    // count user resumes
    @GetMapping("/users/{userId}/resume-count")
    public ResponseEntity<?> getUserResumeCount(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getUserResumeCount(userId));
    }

}