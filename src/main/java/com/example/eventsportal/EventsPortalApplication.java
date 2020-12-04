package com.example.eventsportal;

import com.example.eventsportal.config.jwt.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class EventsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsPortalApplication.class, args);
    }

}
