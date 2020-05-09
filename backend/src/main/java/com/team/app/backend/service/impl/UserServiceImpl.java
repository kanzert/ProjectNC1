package com.team.app.backend.service.impl;

import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.exception.UserAlreadyExistsException;
import com.team.app.backend.dto.UserRegistrationDto;
import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.model.Role;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserStatus;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> searchUsers(String string) {
        return userDao.searchByString(string);
    }


    @Override
    public String getUsernameById(Long id) {
        return userDao.get(id).getUsername();
    }

    @Override
    public User updateUser(UserUpdateDto userDto) {
        User user = new User(userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getImage(),
                new Date(),
                "test",
                userDto.getStatus(),
                userDto.getRole());
        userDao.update(user);
        return userDao.get(user.getId());
    }

    @Override
    public User getUserById(Long id) {
        return userDao.get(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userDao.get(id)!=null){
            userDao.delete(id);
            return true;
        }else{
            return false;
        }

    }

    public User createNewUser(UserCreateDto userCreateDto){
        User user = new User();

        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        //user.setImage(userDto.getImage());
        user.setActivateLink("ttest");
        user.setRegistrationDate(new Date());
        user.setRole(new Role(1L,userCreateDto.getRole().getName()));
        user.setStatus(new UserStatus(1L,"REGISTERED"));
        userDao.save(user);
        return userDao.findByUsername(userCreateDto.getUsername());
    }

    @Override
    public void registerNewUserAccount(UserRegistrationDto userDto)
            throws UserAlreadyExistsException {

        if (isUserRegistered(userDto.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();

        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //user.setImage(userDto.getImage());
        user.setActivateLink("ttest");
        user.setRegistrationDate(new Date());
        user.setRole(new Role(1L,"USER"));
        user.setStatus(new UserStatus(1L,"REGISTERED"));
        userDao.save(user);
    }

    /**
     * checks if user exists in the database
     * @param username user's username
     * @return true if user exists in the database; otherwise false
     */
    @Override
    public boolean isUserRegistered(String username) {
        return userDao.findByUsername(username) != null;
    }

    @Override
    public String getUserPassword(String username) {
        return userDao.getUserPasswordByUsername(username);
    }
}