package com.team.app.backend.rest;


import com.team.app.backend.persistance.model.Setting;
import com.team.app.backend.service.SecurityService;
import com.team.app.backend.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/activity")
public class UserActivityController {

    private final UserActivityService userActivityService;
    private final SecurityService securityService;

    @Autowired
    public UserActivityController(UserActivityService userActivityService, SecurityService securityService) {
        this.userActivityService = userActivityService;
        this.securityService = securityService;
    }

    @GetMapping("/all")
    public ResponseEntity getFriendsActivities() {
        Long user_id = securityService.getCurrentUser().getId();
        return ResponseEntity.ok(userActivityService.getFriendsActivities(user_id));
    }

    @GetMapping("/get-settings")
    public ResponseEntity getFriendsActivitiesSetting() {
        Long user_id = securityService.getCurrentUser().getId();
        return ResponseEntity.ok(userActivityService.getActivitiesSettings(user_id));
    }

    @PostMapping("/set-settings")
    public ResponseEntity setFriendActivitiesSetting(@RequestBody Setting setting){
        userActivityService.setFriendActivitiesSetting(setting);
        return ResponseEntity.ok("");
    }
}
