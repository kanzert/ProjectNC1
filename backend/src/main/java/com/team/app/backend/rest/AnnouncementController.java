package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.Announcement;
import com.team.app.backend.service.AnnouncementService;
import com.team.app.backend.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final MessageSource messageSource;
    private final SecurityService securityService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, MessageSource messageSource, SecurityService securityService) {
        this.announcementService = announcementService;
        this.messageSource = messageSource;
        this.securityService = securityService;

    @PostMapping("/create")
    public ResponseEntity createAnnouncement(@RequestBody Announcement announcement) {
        Map<String, String> response = new HashMap<>();
        announcementService.createAnnouncement(announcement);
        response.put("message",messageSource
                .getMessage("announcement.success", null, LocaleContextHolder.getLocale()));
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/created")
    public ResponseEntity getCreated() {
        List<Announcement> announcementList;
        announcementList = announcementService.getCreated();
        return ResponseEntity.ok(announcementList);
    }


    @GetMapping("/all")
    public ResponseEntity getAll() {
        Long userId = securityService.getCurrentUser().getId();
        List<Announcement> announcementList;
            announcementList = announcementService.getAll(userId);
        return ResponseEntity.ok(announcementList);

    }

    @PostMapping("/approve")
    public ResponseEntity approve(@RequestBody Announcement announcement) {
            announcementService.approve(announcement);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity updateAnnouncement(@RequestBody Announcement announcement) {
        Map<String, String> model = new HashMap<String, String>();
        announcementService.updateAnnouncement(announcement);
        model.put("message",messageSource
                .getMessage("announcement.updated", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAnnouncement(@PathVariable("id") long id) {
        Map<String, String> model = new HashMap<String, String>();
        announcementService.deleteAnnouncement(id);
        model.put("message",messageSource
                .getMessage("announcement.deleted", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(model);
    }
}
