package com.sparta.spartdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpartDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartDeliveryApplication.class, args);
    }

}
