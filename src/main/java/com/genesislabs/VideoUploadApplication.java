package com.genesislabs;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableAspectJAutoProxy
@EnableWebMvc
@SpringBootApplication
public class VideoUploadApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(VideoUploadApplication.class);
    }
}
