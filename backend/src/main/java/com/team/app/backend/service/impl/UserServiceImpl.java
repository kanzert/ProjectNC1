package com.team.app.backend.service.impl;

import com.team.app.backend.dto.UserCreateDto;
import com.team.app.backend.dto.UserUpdateDto;
import com.team.app.backend.exception.UserAlreadyExistsException;
import com.team.app.backend.dto.UserRegistrationDto;
import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.model.Role;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserStatus;
import com.team.app.backend.service.EmailsService;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Locale;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final long ENGLISH_ID = 1L;
    private final long UKRAINE_ID = 1L;


    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    private final EmailsService emailsService;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, JavaMailSender mailSender, EmailsService emailsService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.emailsService = emailsService;
    }


    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> searchUsers(String string, int firstRole, int lastRole) {
        return userDao.searchByString(string, firstRole, lastRole);
    }


    @Override
    public String getUserNameById(Long id) {
        return null;
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
        user.setActivate_link("ttest");
        user.setRegistr_date(new Date());
        user.setRole(new Role(userCreateDto.getRole().getName().equals("admin") ? 3L : 2L ,userCreateDto.getRole().getName()));
        user.setStatus(new UserStatus(2L,"ativated"));
        userDao.save(user);
        return userDao.findByUsername(userCreateDto.getUsername());
    }

    public boolean  checkTokenAvailability(String token){
        return userDao.checkTokenAvailability(token);
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
        user.setPassword(userDto.getPassword());
        //user.setImage(userDto.getImage());
        String token =UUID.randomUUID().toString();
        while(checkTokenAvailability(token)){
            token =UUID.randomUUID().toString();
        }
        user.setActivate_link(token);
        user.setRegistr_date(new Date());
        user.setRole(new Role(1L,"USER"));
        user.setStatus(new UserStatus(1L,"REGISTERED"));

        mailSender.send(emailsService.activationLetter(user));
        userDao.save(user);

    }

    @Override
    public boolean activateUserAccount(String token) {
        boolean check = checkRegistDate(userDao.getUserByToken(token));
        if(check)userDao.activateByToken(token);
        return check;
    }


    @Override
    public boolean checkRegistDate(User user) {
        System.out.println("time"+user.getRegistr_date().getTime() + "    " + new Date().getTime());
        return new Date().getTime()-user.getRegistr_date().getTime()<86400000;
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
    public boolean isEmailRegistered(String email) {
        return userDao.checkEmail(email);
    }

    @Override
    public void sendRecoveryLetter(String email) {
        User user = userDao.getUserByEmail(email);
        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        user.setPassword(passwordEncoder.encode(password));
        mailSender.send(emailsService.recoveryPasswordEmail(user,password));
        userDao.update(user);
    }

    @Override
    public String getUserPassword(String username) {
        return userDao.getUserPasswordByUsername(username);
    }

    @Override
    public void changeLanguage(String lang , Long userId) {
        if(lang.equals("en"))
            userDao.changeLanguage(ENGLISH_ID,userId);
        else
            userDao.changeLanguage(UKRAINE_ID,userId);
    }
    @Override
    public Locale getUserLanguage(Long id) {
        String lan = userDao.getUserLanguage(id);
        if(lan.equals("ua")) {
            return new Locale("ua", "ua");
        } else {
            return Locale.US;
        }
    }

    @Override
    public void setStatus(Long statusId, Long userId) {
        userDao.setStatus(statusId,userId);
    }
}
