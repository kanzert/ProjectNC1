package com.team.app.backend.service;

import com.team.app.backend.persistance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final UserService userService;

    @Autowired
    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userService.getUserByUsername(username);

    }

}
