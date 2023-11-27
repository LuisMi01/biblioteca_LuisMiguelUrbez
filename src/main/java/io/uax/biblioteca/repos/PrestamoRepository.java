package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Bibliotecario;
import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    Prestamo findFirstByLibro(Libro libro);

    Prestamo findFirstByLector(Lector lector);

    Prestamo findFirstByBibliotecario(Bibliotecario bibliotecario);

    Prestamo findFirstByPrestamoLibroLibroes(Libro libro);

    List<Prestamo> findAllByPrestamoLibroLibroes(Libro libro);

}
