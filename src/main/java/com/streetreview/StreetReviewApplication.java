package com.streetreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@EnableMongoAuditing
@EnableJpaRepositories(basePackages = {
        "com.streetreview.member",
        "com.streetreview.review",
        "com.streetreview.reply",
        "com.streetreview.report"
})
@EnableMongoRepositories(basePackages = "com.streetreview.street")
@SpringBootApplication
public class StreetReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreetReviewApplication.class, args);
    }

}
