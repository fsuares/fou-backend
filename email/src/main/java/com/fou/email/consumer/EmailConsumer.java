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

    @RabbitListener(queues = "${spring.rabbitmq.queue}") // Pega o nome do properties
    public void listen(@Payload EmailDto emailDto) {
        try {
            System.out.println("LOG: Mensagem recebida da fila. Processando...");
            emailService.sendEmail(emailDto);
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao enviar e-mail: " + e.getMessage());
        }
    }
}