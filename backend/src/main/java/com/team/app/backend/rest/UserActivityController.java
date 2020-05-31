package com.team.app.backend.rest;


import com.team.app.backend.persistance.model.Setting;
import com.team.app.backend.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/activity")
public class UserActivityController {

    private final
    UserActivityService userActivityService;

    @Autowired
    public UserActivityController(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }


    @GetMapping("/all/{user_id}")
    public ResponseEntity getFriendsActivities(@PathVariable("user_id") long user_id) {
        return ResponseEntity.ok(userActivityService.getFriendsActivities(user_id));
    }
    @GetMapping("/settings/{user_id}")
    public ResponseEntity getFriendsActivitiesSetting(@PathVariable("user_id") long user_id) {
        return ResponseEntity.ok(userActivityService.getActivitiesSettings(user_id));
    }
    @PostMapping("/settings")
    public ResponseEntity setFriendActivitiesSetting(@RequestBody Setting setting){
        userActivityService.setFriendActivitiesSetting(setting);
        return ResponseEntity.ok("");
    }
}
