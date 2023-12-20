package com.streetreview.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class Config {
    @Value("${slack.webhook-url}")
    private String slackWebhookUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public SlackMessage slackMessage() {
        return new SlackMessage(restTemplate(), slackWebhookUrl);
    }
}
