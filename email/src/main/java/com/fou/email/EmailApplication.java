package com.fou.email;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    @Bean
    public Queue createQueue() {
        // Creates the queue automatically if it doesn't exist
        return new Queue("email-queue", true);
    }
}