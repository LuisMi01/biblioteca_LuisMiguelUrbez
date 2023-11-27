package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.PoliticaPrestamo;
import io.uax.biblioteca.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PoliticaPrestamoRepository extends JpaRepository<PoliticaPrestamo, Integer> {

    List<PoliticaPrestamo> findAllByPoliticaPrestamoUsuarioUsuarios(Usuario usuario);

}
