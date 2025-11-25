package com.fou.email.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Bean
    public Queue queue() {
        // Cria a fila se ela não existir. "true" significa que é durável (não some ao reiniciar)
        return new Queue(queueName, true);
    }
}