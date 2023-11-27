package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectorRepository extends JpaRepository<Lector, Integer> {

    Lector findFirstByMultas(Multas multas);

    Lector findFirstByLectorPrestamoPrestamoes(Prestamo prestamo);

    List<Lector> findAllByLectorPrestamoPrestamoes(Prestamo prestamo);

}
