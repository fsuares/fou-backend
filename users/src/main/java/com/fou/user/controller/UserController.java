package com.fou.user.controller;

import com.fou.user.dto.EmailDto; // Certifique-se que este import existe
import com.fou.user.model.User;
import com.fou.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/notify-all")
    public ResponseEntity<String> notifyAllUsers(@RequestParam String subject, @RequestParam String message) {
        userService.sendNotificationToAll(subject, message);
        return ResponseEntity.ok("Processo de envio em massa iniciado com sucesso!");
    }
}