package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.TransaccionLibro;
import io.uax.biblioteca.model.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;




public interface TransaccionLibroRepository extends JpaRepository<TransaccionLibro, Integer> {
    List<TransaccionLibro> findByEstado(EstadoLibro estado);
}
