package com.wbrawner.flayre.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlayreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlayreApplication.class, args);
    }
}
