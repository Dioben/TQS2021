package config;

import com.example.homework.HomeworkApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class BeanConfigs {
    @Bean
    Logger logger(){return  LoggerFactory.getLogger(HomeworkApplication.class);}
}
