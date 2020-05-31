package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.AnnouncementDao;
import com.team.app.backend.persistance.dao.mappers.AnnouncementRowMapper;
import com.team.app.backend.persistance.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    private final JdbcTemplate jdbcTemplate;

    private final Environment env;

    private final AnnouncementRowMapper announcementRowMapper;

    @Autowired
    public AnnouncementDaoImpl(DataSource dataSource, Environment env, AnnouncementRowMapper announcementRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
        this.announcementRowMapper = announcementRowMapper;
    }



    @Override
    public void create(Announcement announcement) {
        jdbcTemplate.update(
                env.getProperty("create.announcement"),
                announcement.getTitle(),
                announcement.getText(),
                announcement.getDate(),
                announcement.getImage(),
                announcement.getStatusId(),
                announcement.getCategoryId(),
                announcement.getUserId()
        );
    }

    @Override
    public void update(Announcement announcement) {
        jdbcTemplate.update(
                env.getProperty("update.announcement"),
                announcement.getTitle(),
                announcement.getText(),
                announcement.getDate(),
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
                env.getProperty("delete.announcement"),
                id
        );
    }
    @Override
    public List<Announcement> getCreated() {

        return jdbcTemplate.query(env.getProperty("get.created.announcement")
                ,announcementRowMapper);

    }
    @Override
    public List<Announcement> getAll(Long userId) {
        return jdbcTemplate.query(env.getProperty("get.all.announcement"),
                announcementRowMapper);

    }
    @Override
    public void approve(Long id) {
        jdbcTemplate.update(env.getProperty("approve.announcement"), id);
    }


    @Override
    public Announcement get(Long id) {
        return jdbcTemplate.queryForObject(env.getProperty("get.announcement"),
                new Object[]{id},
                announcementRowMapper);
    }

}
