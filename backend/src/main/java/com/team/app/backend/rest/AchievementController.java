package com.team.app.backend.rest;


import com.team.app.backend.persistance.model.Achievement;
import com.team.app.backend.persistance.model.UserAchievement;
import com.team.app.backend.service.AchievementService;
import com.team.app.backend.service.SecurityService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/achievement")
public class AchievementController {
    private final AchievementService achievementService;
    private final MessageSource messageSource;
    private final SecurityService securityService;


    @Autowired
    public AchievementController(AchievementService achievementService, MessageSource messageSource) {
        this.achievementService = achievementService;
        this.messageSource = messageSource;
    }


    public AchievementController(AchievementService achievementService, MessageSource messageSource, SecurityService securityService) {
        this.achievementService = achievementService;
        this.messageSource = messageSource;
        this.securityService = securityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Achievement>> getAchievements() {
        return ResponseEntity.ok().body(achievementService.getAchievements());
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserAchievement>> getUserAchievements() {
        Long id = securityService.getCurrentUser().getId();
        return ResponseEntity.ok().body(achievementService.getUserAchievements(id));
    }

    @PostMapping("/set")
    public ResponseEntity checkUserAchievements() {
        Long id = securityService.getCurrentUser().getId();
        Map<String, String> response = new HashMap<>();
        achievementService.setUserAchievement(id);
        response.put("message", "Achievement was set!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity createAchievement(@RequestBody Achievement achievement) {
        Map<String, String> response = new HashMap<>();
        achievementService.createAchievement(achievement);
        response.put("message", messageSource.getMessage("achievement.success", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(response);
    }

}
