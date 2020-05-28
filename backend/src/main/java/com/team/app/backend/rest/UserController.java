package com.team.app.backend.rest;


import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/user/search/{name}/{first}/{last}")
    public List<User> searchUser(
            @PathVariable("name") String name,
            @PathVariable("first") int firstRole,
            @PathVariable("last") int lastRole) {
        System.out.println(firstRole);
        return userService.searchUsers(name, firstRole, lastRole);
    }


    @PutMapping("/user/update")
    public User updateUser(
                           @RequestBody UserUpdateDto userUpdateDto) {
        System.out.println(userUpdateDto.getFirstName()+"   "+userUpdateDto.getLastName());
        return userService.updateUser(userUpdateDto);
    };


    @PostMapping("user/create")
    public User createUser(
            @RequestBody UserCreateDto userDto){
        return userService.createNewUser(userDto);
    }

    @GetMapping("user/activate")
    public ModelAndView activateUser(@RequestParam("token") String token){
        if(userService.activateUserAccount(token)) return new ModelAndView("redirect:" + "https://brainduel.herokuapp.com/#/login" );
        return new ModelAndView("redirect:" + "https://brainduel.herokuapp.com/#/signup" );
    }


    @DeleteMapping("user/delete/{id}")
    public Map<String,Object> deleteUser( @PathVariable("id") long id){
        Map<String, Object> model = new HashMap<String, Object>();
        if(userService.deleteUser(id)){

            model.put("message", messageSource.getMessage("delete.user.success", null, LocaleContextHolder.getLocale()));
        }else{
            model.put("message", messageSource.getMessage("delete.user.bad", null, LocaleContextHolder.getLocale()));
        }

        return model;
    }


}
