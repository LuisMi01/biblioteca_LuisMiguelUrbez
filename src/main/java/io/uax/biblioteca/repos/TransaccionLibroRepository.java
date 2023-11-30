package io.uax.biblioteca.repos;

import io.uax.biblioteca.domain.TransaccionLibro;
import io.uax.biblioteca.model.EstadoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;




public interface TransaccionLibroRepository extends JpaRepository<TransaccionLibro, Integer> {
    @Modifying
    @Query("UPDATE TransaccionLibro t SET t.estado = ?2 WHERE t.id = ?1")
    void updateEstado(Integer id, EstadoLibro nuevoEstado);
}
