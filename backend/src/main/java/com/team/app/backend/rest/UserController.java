package com.team.app.backend.rest;


import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.service.SecurityService;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource, SecurityService securityService) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.securityService = securityService;
    }

    @GetMapping("/user/search/{name}/{first}/{last}")
    public List<User> searchUser(
            @PathVariable("name") String name,
            @PathVariable("first") int firstRole,
            @PathVariable("last") int lastRole) {
        return userService.searchUsers(name, firstRole, lastRole);
    }

    @PutMapping("/user/update")
    public User updateUser(
                           @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @PostMapping("user/create")
    public User createUser(
            @RequestBody UserCreateDto userDto){
        return userService.createNewUser(userDto);
    }

    @GetMapping("user/activate")
    public ModelAndView activateUser(@RequestParam("token") String token){
        if(userService.activateUserAccount(token)) return new ModelAndView("redirect:" + "https://brainduel.herokuapp.com/#/login" );
        return new ModelAndView("redirect:" + "https://brainduel.herokuapp.com/#/signup" );
        //else return ResponseEntity.ok(messageSource.getMessage("registry.bad", null, LocaleContextHolder.getLocale()));
    }

    @DeleteMapping("user/delete/")
    public Map<String,Object> deleteUser(){
        Long id = securityService.getCurrentUser().getId();
        Map<String, Object> model = new HashMap<String, Object>();
        if(userService.deleteUser(id)){

            model.put("message", messageSource
                    .getMessage("delete.user.success", null, LocaleContextHolder.getLocale()));
        }else{
            model.put("message", messageSource
                    .getMessage("delete.user.bad", null, LocaleContextHolder.getLocale()));
        }

        return model;
    }

    @PostMapping("/user/status/{id}")
    public ResponseEntity setStatus(@PathVariable("id") Long statusId) {
        Long userId = securityService.getCurrentUser().getId();
        try{
            userService.setStatus(statusId,userId);
        }
        catch (DataAccessException sqlEx) {
           return ResponseEntity.badRequest().body(messageSource
                   .getMessage("set.status.user.bad", null, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/current")
    public User getCurrentUser() {
        return securityService.getCurrentUser();
    }
}
