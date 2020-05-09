package com.team.app.backend.service;


import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.exception.UserAlreadyExistsException;
import com.team.app.backend.dto.UserRegistrationDto;
import com.team.app.backend.persistance.model.User;

import java.util.List;

public interface UserService {

    String getUsernameById(Long id);

    User updateUser(UserUpdateDto userDto);

    User getUserById(Long id);

    User getUserByUsername(String username);

    boolean deleteUser(Long id);

    User createNewUser(UserCreateDto userCreateDto);

    List<User> searchUsers(String string);

    void registerNewUserAccount(UserRegistrationDto userDto)
            throws UserAlreadyExistsException;

    boolean isUserRegistered(String username);

    String getUserPassword(String username);

    User findByUsername(String username);
}
