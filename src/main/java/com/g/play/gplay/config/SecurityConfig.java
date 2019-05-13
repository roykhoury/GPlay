package com.g.play.gplay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class is used to restrict the API to certain users
 * having specific roles
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Authentication : User --> Roles
     * Add an ADMIN user into the database
     *
     * @param auth the auth object
     * @throws Exception if anything goes wrong during the config
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
                .withUser("roykhoury").password("adminPower")
                .roles("USER", "ADMIN");
    }

    /**
     * Authorization : Role -> Access
     * Assign the "all" access to the "Admin" role
     *
     * @param http the HTTP security object
     * @throws Exception
     */
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/**").hasRole("ADMIN").and()
                .csrf().disable().headers().frameOptions().disable();
    }
}