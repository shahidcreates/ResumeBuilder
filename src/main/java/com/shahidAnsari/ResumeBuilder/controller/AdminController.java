package com.shahidAnsari.ResumeBuilder.controller;
import com.shahidAnsari.ResumeBuilder.dto.UserResumeStatsDto;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import static com.shahidAnsari.ResumeBuilder.util.AppConstants.*;

@Tag(
        name = "Admin REST APIs"
)
@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // get all user
    @Operation(
            summary = "For Get All Users ",
            description = "REST API for Get users"
    )
    @GetMapping(ALL_USERS)
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }


    // delete user by id
    @Operation(
            summary = "Delete User By ID",
            description = "Delete a user using the provided user ID"
    )
    @DeleteMapping(USER_BY_ID)
    public ResponseEntity<?> deleteUser(@Parameter(description = "User ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }


    // get user by id
    @Operation(
            summary = "Get User By ID",
            description = "Fetch user details using user ID"
    )
    @GetMapping(USER_BY_ID)
    public ResponseEntity<?> getUser(@Parameter(description = "User ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }


    // change user Role
    @Operation(
            summary = "Update User Role",
            description = "Update role of a specific user. Example body: {\"role\":\"ADMIN\"}"
    )
    @PutMapping(USER_ROLE)
    public ResponseEntity<?> updateRole(@Parameter(description = "User ID", example = "1") @PathVariable Long id,
                                        @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(adminService.updateUserRole(id, body));
    }


    // Block user
    @Operation(
            summary = "Disable User",
            description = "Block or disable a user account"
    )
    @PutMapping(USER_DISABLE)
    public ResponseEntity<?> disableUser(@Parameter(description = "User ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(adminService.disableUser(id));
    }


    // Unblock user
    @Operation(
            summary = "Enable User",
            description = "Enable or unblock a user account"
    )
    @PutMapping(USER_ENABLE)
    public ResponseEntity<?> enableUser(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long id){

        return ResponseEntity.ok(adminService.enableUser(id));
    }


    // STATS
    @Operation(
            summary = "Get Application Statistics",
            description = "Fetch overall application statistics"
    )
    @GetMapping(STATS)
    public ResponseEntity<?> stats() {
        return ResponseEntity.ok(adminService.stats());
    }


    // GET RESUMES
    @Operation(
            summary = "Get All Resumes",
            description = "Fetch all resumes available in the system"
    )
    @GetMapping(ALL_RESUMES)
    public ResponseEntity<?> resumes() {
        return ResponseEntity.ok(adminService.getResumes());
    }


    // DASHBOARD
    @Operation(
            summary = "Get Dashboard Data",
            description = "Fetch admin dashboard information"
    )
    @GetMapping(DASHBOARD)
    public ResponseEntity<?> dashboard() {
        return ResponseEntity.ok(adminService.dashboard());
    }


    // count user resumes
    @Operation(
            summary = "Get User Resume Count",
            description = "Fetch total number of resumes created by a user"
    )
    @GetMapping(USER_RESUMES)
    public ResponseEntity<?> getUserResumeCount(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId) {

        return ResponseEntity.ok(adminService.getUserResumeCount(userId));
    }


    // get user resumes stats
    @Operation(
            summary = "Get User Resume Statistics",
            description = "Fetch resume statistics for all users"
    )
    @GetMapping(USER_RESUMES_STATS)
    public List<UserResumeStatsDto> getUserResumeStats() {
        return adminService.getUserResumeStats();
    }

}