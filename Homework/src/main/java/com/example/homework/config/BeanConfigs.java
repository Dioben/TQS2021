package com.example.homework.config;

import com.example.homework.HomeworkApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfigs {
    @Bean
    Logger logger(){return  LoggerFactory.getLogger(HomeworkApplication.class);}
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
