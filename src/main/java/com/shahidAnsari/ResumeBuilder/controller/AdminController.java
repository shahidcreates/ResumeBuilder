package com.shahidAnsari.ResumeBuilder.controller;
import com.shahidAnsari.ResumeBuilder.entity.User;
import com.shahidAnsari.ResumeBuilder.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import static com.shahidAnsari.ResumeBuilder.util.AppConstants.*;

@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping(ALL_USERS)
    public List<User> getUsers(){
        return adminService.getAllUsers();
    }


    @DeleteMapping(USER_BY_ID)
    public String deleteUser(@PathVariable Long id){
        return adminService.deleteUser(id);
    }

    @GetMapping(USER_BY_ID)
    public User getUserById(@PathVariable Long id){
        return adminService.getUserById(id);
    }

    @PutMapping(USER_ROLE)
    public String updateRole(@PathVariable Long id, @RequestBody Map<String,String> body){
        return adminService.updateUserRole(id,body);
    }

    @PutMapping(USER_DISABLE)
    public String disableUser(@PathVariable Long id){
        return adminService.disableUser(id);
    }

    @PutMapping(USER_ENABLE)
    public String enableUser(@PathVariable Long id){
        return adminService.enableUser(id);
    }

    @GetMapping(STATS)
    public Map<String,Long> stats(){
        return adminService.stats();
    }
}