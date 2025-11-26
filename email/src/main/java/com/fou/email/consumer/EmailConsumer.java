package com.fou.email.consumer;

import com.fou.email.dto.EmailDto;
import com.fou.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto) {
        System.out.println("DEBUG: Mensagem chegou no EmailService! Para: " + emailDto.getEmailTo());

        try {
            emailService.sendEmail(emailDto);
        } catch (Exception e) {
            System.err.println("ERRO CRÍTICO AO ENVIAR E-MAIL:");
            e.printStackTrace();
        }
    }
}