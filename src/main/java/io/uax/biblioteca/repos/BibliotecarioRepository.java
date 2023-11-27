package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.domain.Bibliotecario;
import io.uax.biblioteca.domain.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Integer> {

    Bibliotecario findFirstByBibliotecarioLibroLibroes(Libro libro);

    List<Bibliotecario> findAllByBibliotecarioBibliotecaBibliotecas(Biblioteca biblioteca);

    List<Bibliotecario> findAllByBibliotecarioLibroLibroes(Libro libro);

}
