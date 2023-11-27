package io.uax.biblioteca.service;

import io.uax.biblioteca.domain.TransaccionLibro;
import io.uax.biblioteca.model.TransaccionLibroDTO;
import io.uax.biblioteca.repos.TransaccionLibroRepository;
import io.uax.biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransaccionLibroService {

    private final TransaccionLibroRepository transaccionLibroRepository;

    public TransaccionLibroService(final TransaccionLibroRepository transaccionLibroRepository) {
        this.transaccionLibroRepository = transaccionLibroRepository;
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
        transaccionLibroDTO.setFechaTransaccion(transaccionLibro.getFechaTransaccion());
        transaccionLibroDTO.setAccion(transaccionLibro.getAccion());
        transaccionLibroDTO.setDetalles(transaccionLibro.getDetalles());
        return transaccionLibroDTO;
    }

    private TransaccionLibro mapToEntity(final TransaccionLibroDTO transaccionLibroDTO,
            final TransaccionLibro transaccionLibro) {
        transaccionLibro.setFechaTransaccion(transaccionLibroDTO.getFechaTransaccion());
        transaccionLibro.setAccion(transaccionLibroDTO.getAccion());
        transaccionLibro.setDetalles(transaccionLibroDTO.getDetalles());
        return transaccionLibro;
    }

}
