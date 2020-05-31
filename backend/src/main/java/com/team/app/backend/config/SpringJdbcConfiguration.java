package com.team.app.backend.config;

import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.dao.impl.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.team.app.backend")
public class SpringJdbcConfiguration {
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://ec2-46-137-79-235.eu-west-1.compute.amazonaws.com:5432/d9slfpqhmrvt6o");
        dataSource.setUsername("vgicoasopqmert");
        dataSource.setPassword("c8251c662c253a247023ad3dab0bf1891a4c99c0db50781ee5e271045e8d5452");
        return dataSource;
    }

}

