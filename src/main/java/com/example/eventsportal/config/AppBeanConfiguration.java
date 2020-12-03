package com.example.eventsportal.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@Configuration
public class AppBeanConfiguration{

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}






