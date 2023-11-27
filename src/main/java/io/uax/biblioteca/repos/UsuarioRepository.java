package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
