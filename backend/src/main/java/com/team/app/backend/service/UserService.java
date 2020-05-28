package com.team.app.backend.service;


import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.exception.UserAlreadyExistsException;
import com.team.app.backend.dto.UserRegistrationDto;
import com.team.app.backend.persistance.model.User;

import java.util.List;
import java.util.Locale;

public interface UserService {


    //TODO: IMPLEMENT
    String getUserNameById(Long id);

    void changeLanguage(String lang , Long userId);

    User updateUser(UserUpdateDto userDto);

    User getUserById(Long id);

    User getUserByUsername(String username);

    boolean deleteUser(Long id);

    boolean  checkTokenAvailability(String token);

    User createNewUser(UserCreateDto userCreateDto);

    List<User> searchUsers(String string, int firstRole, int lastRole);

    void registerNewUserAccount(UserRegistrationDto userDto)
            throws UserAlreadyExistsException;

    boolean activateUserAccount(String token);

    boolean checkRegistDate(User user);

    boolean isUserRegistered(String username);

    boolean isEmailRegistered(String email);

    void sendRecoveryLetter(String email);

    String getUserPassword(String username);

    User findByUsername(String username);

    Locale getUserLanguage(Long id);

    void setStatus(Long statusId, Long userId);
}
