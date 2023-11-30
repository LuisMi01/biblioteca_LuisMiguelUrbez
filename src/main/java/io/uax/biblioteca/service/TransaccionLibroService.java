package io.uax.biblioteca.service;

import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.TransaccionLibro;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.EstadoLibro;
import io.uax.biblioteca.model.TransaccionLibroDTO;
import io.uax.biblioteca.repos.LibroRepository;
import io.uax.biblioteca.repos.TransaccionLibroRepository;
import io.uax.biblioteca.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TransaccionLibroService {

    private final TransaccionLibroRepository transaccionLibroRepository;
    private final LibroRepository libroRepository;

    public TransaccionLibroService(TransaccionLibroRepository transaccionLibroRepository, LibroRepository libroRepository) {
        this.transaccionLibroRepository = transaccionLibroRepository;
        this.libroRepository = libroRepository;
    }

    public List<TransaccionLibroDTO> findAll() {
        final List<TransaccionLibro> transaccionLibroes = transaccionLibroRepository.findAll(Sort.by("id"));
        return transaccionLibroes.stream()
                .map(transaccionLibro -> mapToDTO(transaccionLibro, new TransaccionLibroDTO()))
                .toList();
    }

    public TransaccionLibroDTO get(final Integer id) {
        return transaccionLibroRepository.findById(id)
                .map(transaccionLibro -> mapToDTO(transaccionLibro, new TransaccionLibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TransaccionLibroDTO transaccionLibroDTO) {
        final TransaccionLibro transaccionLibro = new TransaccionLibro();
        mapToEntity(transaccionLibroDTO, transaccionLibro);
        return transaccionLibroRepository.save(transaccionLibro).getId();
    }

    public void update(final Integer id, final TransaccionLibroDTO transaccionLibroDTO) {
        final TransaccionLibro transaccionLibro = transaccionLibroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transaccionLibroDTO, transaccionLibro);
        transaccionLibroRepository.save(transaccionLibro);
    }

    public void delete(final Integer id) {
        transaccionLibroRepository.deleteById(id);
    }

    private TransaccionLibroDTO mapToDTO(final TransaccionLibro transaccionLibro,
                                         final TransaccionLibroDTO transaccionLibroDTO) {
        transaccionLibroDTO.setId(transaccionLibro.getId());
        transaccionLibroDTO.setLibro(transaccionLibro.getLibro().getAutor());
        transaccionLibroDTO.setNombreLibro(transaccionLibro.getLibro().getTitulo());
        transaccionLibroDTO.setEstado(transaccionLibro.getEstado());
        return transaccionLibroDTO;
    }

    private TransaccionLibro mapToEntity(final TransaccionLibroDTO transaccionLibroDTO,
            final TransaccionLibro transaccionLibro) {
        transaccionLibro.setLibro(libroRepository.findByTitulo(transaccionLibroDTO.getLibro()));
        transaccionLibro.setEstado(transaccionLibroDTO.getEstado());
        transaccionLibro.setId(transaccionLibroDTO.getId());
        return transaccionLibro;
    }

    public List<Libro> getLibrosRotos() {
        return libroRepository.findByEstado(EstadoLibro.ROTO);

        /*List<TransaccionLibro> transaccionesRotos = transaccionLibroRepository.findByEstado(EstadoLibro.ROTO);

        return transaccionesRotos.stream()
                .map(TransaccionLibro::getLibro)
                .collect(Collectors.toList());*/
    }


    public void arreglarLibro() {
        // Implementar lógica para cambiar el estado del libro a NUEVO
        // Puedes obtener el TransaccionLibro por su ID, actualizar el estado y guardar.
    }

}
