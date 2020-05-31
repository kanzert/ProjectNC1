package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.dao.mappers.UserRowMapper;
import com.team.app.backend.persistance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRowMapper userRowMapper;

    @Autowired
    private Environment env;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    @Override
    public void save(User user) {
        jdbcTemplate.update(
                env.getProperty("save.user"),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getImage(),
                user.getRegistr_date(),
                user.getActivate_link(),
                user.getStatus().getId(),
                user.getRole().getId()
        );
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                env.getProperty("update.user"),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getImage(),
                user.getRegistr_date(),
                user.getActivate_link(),
                user.getStatus().getId(),
                user.getRole().getId(),
                user.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                env.getProperty("delete.user.by.id"),
                id
        );
    }


    @Override
    public User get(Long id) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.user.by.id"),
                new Object[]{id},
                userRowMapper);
    }

    @Override
    public User findByUsername(String username) {
        List<User> userslist=jdbcTemplate.query(
                env.getProperty("get.user.by.username"),
                new Object[]{username},
                userRowMapper);
        if(userslist.size()==0){
            return null;
        }else{
            return userslist.get(0);
        }

    }

    @Override
    public String getUserPasswordByUsername(String username) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.pass.by.username"),
                new Object[]{username},String.class
        );
    }


    @Override
    public User getUserByToken(String token) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.user.by.token"),
                new Object[]{token},
                userRowMapper);    }



    @Override
    public void activateByToken(String token) {
        jdbcTemplate.update(
                env.getProperty("activate.token"),
                token
        );
    }

    @Override
    public boolean checkTokenAvailability(String token) {
        return jdbcTemplate.queryForObject(
                env.getProperty("check.token"),
                new Object[]{token},Boolean.class
        );
    }

    @Override
    public boolean checkEmail(String email) {
        return jdbcTemplate.queryForObject(
                env.getProperty("check.email"),
                new Object[]{email},Boolean.class
        );
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.user.by.email"),
                new Object[]{email},
                userRowMapper);
    }

    public void changeLanguage(Long langId , Long userId) {
        jdbcTemplate.update(env.getProperty("set.user.language"),
                langId, userId
        );
    }

    @Override
    public String getUserLanguage(Long id) {
        return jdbcTemplate.queryForObject(env.getProperty("get.user.language"),
                new Object[]{id},String.class
        );
    }

    @Override
    public List<User> searchByString(String searchstring, int firstRole, int lastRole) {
        String search="%"+searchstring+"%";
        String sql=env.getProperty("search.user");
        return jdbcTemplate.query(sql,new Object[]{search,search,search, firstRole, lastRole}, userRowMapper);
    }

    @Override
    public void setStatus(Long statusId, Long userId) {
        jdbcTemplate.update(env.getProperty("set.user.status"),
                statusId, userId
        );
    }
}
