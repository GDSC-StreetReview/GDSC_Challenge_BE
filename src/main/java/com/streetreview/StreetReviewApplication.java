package com.streetreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StreetReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreetReviewApplication.class, args);
    }

}
