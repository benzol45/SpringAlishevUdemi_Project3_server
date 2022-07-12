package com.example.sensor_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SpringbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbaseApplication.class, args);
    }

}