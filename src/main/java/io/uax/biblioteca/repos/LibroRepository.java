package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.model.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro, Integer> {
    Libro findByTitulo(String libro);

    List<Libro> findByEstado(EstadoLibro estadoLibro);
}
