package com.team.app.backend.config;


import com.team.app.backend.security.jwt.JwtConfigurer;
import com.team.app.backend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/api/login";
    private static final String SIGN_UP_ENDPOINT = "/api/sign-up";
    private static final String ROOT_ENDPOINT = "/";
    private static final String SOCKET_ENDPOINT = "/ws";
    private static final String ACTIVATE_ENDPOINT = "/api/user/activate*";
    private static final String RECOVERY_ENDPOINT = "/api/recovery";


    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests().and()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers(SIGN_UP_ENDPOINT).permitAll()
                .antMatchers(ROOT_ENDPOINT).permitAll()
                .antMatchers(RECOVERY_ENDPOINT).permitAll()
                .antMatchers(SOCKET_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET,ACTIVATE_ENDPOINT + "*")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider)).and().cors();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/index.html",
                "/polyfills*.js",
                "/runtime*.js",
                "/styles*.js",
                "/scripts.js",
                "https://brainduel.herokuapp.com*",
                "/main*.js",
                "/ua.svg",
                "/gb.svg",
                "/assets/i18n/en.json",
                "/assets/i18n/ua.json",
                "/assets/loader_brainduel.svg",
                "/assets/logo_brainduel.png",
                "/vendor*.js",
                "/assets/stomp.min.js",
                "/assets/sockjs.min.js",
                "/ws/**"
        );
    }
}
