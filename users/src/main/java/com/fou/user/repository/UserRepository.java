package com.fou.user.repository;

import com.fou.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // Método extra para buscar por email, se necessário
    boolean existsByEmail(String email);
}
