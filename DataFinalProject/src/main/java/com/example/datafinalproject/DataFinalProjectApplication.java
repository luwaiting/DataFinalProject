package com.example.datafinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication
//@EnableWebMvc
//@ServletComponentScan(basePackages ={"com"})
//@ComponentScan(basePackages ={"com"})
public class DataFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataFinalProjectApplication.class, args);
    }

}
