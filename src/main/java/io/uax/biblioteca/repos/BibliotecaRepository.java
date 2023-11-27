package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer> {
}
