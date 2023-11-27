package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, Integer> {
}
