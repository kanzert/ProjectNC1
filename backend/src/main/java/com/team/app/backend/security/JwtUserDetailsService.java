package com.team.app.backend.security;

import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.security.jwt.JwtUserFactory;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userDao.findByUsername(username);

       if (user == null) {
           throw new UsernameNotFoundException("User with username " + username + " not found.");
       }
       return JwtUserFactory.create(user);
    }
}
