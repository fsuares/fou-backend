package com.fou.user.service;

import com.fou.user.dto.EmailDto;
import com.fou.user.model.User;
import com.fou.user.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    // --- NEW METHOD (Producer) ---
    public void sendNotificationToAll(String subject, String messageContent) {
        List<User> users = userRepository.findAll();

        System.out.println("LOG: Iniciando processo de envio para " + users.size() + " usuários.");

        for (User user : users) {
            String personalizedText = "Olá " + user.getName() + ",\n\n" + messageContent;

            EmailDto emailDto = new EmailDto(
                    user.getId(),
                    user.getEmail(),
                    subject,
                    personalizedText
            );

            rabbitTemplate.convertAndSend("email-queue", emailDto);
        }
    }
}
