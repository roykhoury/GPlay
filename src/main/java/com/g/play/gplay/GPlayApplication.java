package com.g.play.gplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class GPlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GPlayApplication.class, args);
    }
}
