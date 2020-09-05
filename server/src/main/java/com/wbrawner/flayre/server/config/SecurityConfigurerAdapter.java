package com.wbrawner.flayre.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    public SecurityConfigurerAdapter(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(environment.getProperty("flayre.admin.user")).password(passwordEncoder().encode(environment.getProperty("flayre.admin.password")))
                .authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/flayre.js")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/events")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .cors()
                .configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.applyPermitDefaultValues();
                    corsConfig.setAllowedOrigins(Collections.singletonList("*"));
                    corsConfig.setAllowedMethods(Arrays.asList(HttpMethod.POST.name(), HttpMethod.OPTIONS.name()));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                })
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
