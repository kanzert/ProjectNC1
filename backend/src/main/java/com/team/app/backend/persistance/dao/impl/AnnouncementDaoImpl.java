package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.AnnouncementDao;
import com.team.app.backend.persistance.dao.mappers.AnnouncementRowMapper;
import com.team.app.backend.persistance.dao.mappers.UserRowMapper;
import com.team.app.backend.persistance.model.Announcement;
import com.team.app.backend.persistance.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private AnnouncementRowMapper userRowMapper = new AnnouncementRowMapper();

    public AnnouncementDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    @Override
    public void create(Announcement announcement) {
        jdbcTemplate.update(
                "INSERT INTO announcement(title, text, date, image, status_id, cat_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                announcement.getTitle(),
                announcement.getText(),
                LocalDateTime.parse(announcement.getDate()),
                announcement.getImage(),
                announcement.getStatusId(),
                announcement.getCategoryId(),
                announcement.getUserId()
        );
    }

    @Override
    public void update(Announcement announcement) {
        jdbcTemplate.update(
                "UPDATE announcement set title = ?, text = ?, date = ?, image = ?, status_id = ?, cat_id = ?, user_id = ? where id = ?",
                announcement.getTitle(),
                announcement.getText(),
                LocalDateTime.parse(announcement.getDate()),
                announcement.getImage(),
                announcement.getStatusId(),
                announcement.getCategoryId(),
                announcement.getUserId(),
                announcement.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE from announcement where id = ?",
                id
        );
    }


    @Override
    public Announcement get(Long id) {
        return new Announcement();
//        return jdbcTemplate.queryForObject(
//                "select U.id,U.firstname,U.lastname,U.username,U.image,U.password,U.email,U.registr_date,U.activate_link,U.status_id,US.name as status_name,U.role_id,R.name as role_name from users U INNER JOIN user_status US ON U.status_id = US.id INNER JOIN role R ON R.id = U.role_id where U.id = ? ",
//                new Object[]{id},
//                userRowMapper);
    }



}
