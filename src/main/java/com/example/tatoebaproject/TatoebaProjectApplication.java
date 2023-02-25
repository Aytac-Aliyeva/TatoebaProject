package com.example.tatoebaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TatoebaProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(TatoebaProjectApplication.class, args);

    }
}
