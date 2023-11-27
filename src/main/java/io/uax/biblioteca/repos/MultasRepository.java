package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MultasRepository extends JpaRepository<Multas, Integer> {

    Multas findFirstByPrestamo(Prestamo prestamo);

    Multas findFirstByMultasLectorLectors(Lector lector);

    List<Multas> findAllByMultasLectorLectors(Lector lector);

}
