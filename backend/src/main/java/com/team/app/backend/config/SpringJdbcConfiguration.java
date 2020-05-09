package com.team.app.backend.config;

import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.dao.impl.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.team.app.backend")
public class SpringJdbcConfiguration {
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://ec2-54-217-204-34.eu-west-1.compute.amazonaws.com:5432/d2q1r015780tbg");
        dataSource.setUsername("auewlydijtwktd");
        dataSource.setPassword("571df7407fd1073d91d2d43e3c085880564337693979e4e00e7bce1771228bf9");
        return dataSource;
    }
}
//import javax.sql.DataSource;

//@Configuration
//@ComponentScan("com.team.app.backend")
//public class SpringJdbcConfiguration {
//    @Bean
//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://ec2-54-86-170-8.compute-1.amazonaws.com:5432/d7o2ab5h56gflf");
//        dataSource.setUsername("wadvndyharmdmq");
//        dataSource.setPassword("2991f50198205f0a162e5ba5980c01661eda54e628764b9411e87747334de465");
//        return dataSource;
//    }
//
//    @Bean
//    public UserDao getUserDao() {
//        return new UserDaoImpl(getDataSource());
//    }
//}
