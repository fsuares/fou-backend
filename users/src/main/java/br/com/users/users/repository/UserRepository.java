package br.com.users.users.repository;

import br.com.users.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}
