package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.TransaccionLibro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransaccionLibroRepository extends JpaRepository<TransaccionLibro, Integer> {
}
