package com.fou.email.service;

import com.fou.email.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fou.com"); // Quem está enviando
        message.setTo(emailDto.getEmailTo());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getText());

        emailSender.send(message); // Dispara o e-mail real

        // Log para vermos no console
        System.out.println("LOG: E-mail enviado com sucesso para: " + emailDto.getEmailTo());
    }
}