package io.uax.biblioteca.service;

import io.uax.biblioteca.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.domain.PoliticaPrestamo;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.PoliticaPrestamoDTO;
import io.uax.biblioteca.repos.BibliotecaRepository;
import io.uax.biblioteca.repos.PoliticaPrestamoRepository;
import io.uax.biblioteca.repos.UsuarioRepository;
import io.uax.biblioteca.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PoliticaPrestamoService {

    private final PoliticaPrestamoRepository politicaPrestamoRepository;
    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;

    public PoliticaPrestamoService(final PoliticaPrestamoRepository politicaPrestamoRepository,
            final BibliotecaRepository bibliotecaRepository,
            final UsuarioRepository usuarioRepository) {
        this.politicaPrestamoRepository = politicaPrestamoRepository;
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PoliticaPrestamoDTO> findAll() {
        final List<PoliticaPrestamo> politicaPrestamoes = politicaPrestamoRepository.findAll(Sort.by("id"));
        return politicaPrestamoes.stream()
                .map(politicaPrestamo -> mapToDTO(politicaPrestamo, new PoliticaPrestamoDTO()))
                .toList();
    }

    public PoliticaPrestamoDTO get(final Integer id) {
        return politicaPrestamoRepository.findById(id)
                .map(politicaPrestamo -> mapToDTO(politicaPrestamo, new PoliticaPrestamoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PoliticaPrestamoDTO politicaPrestamoDTO) {
        final PoliticaPrestamo politicaPrestamo = new PoliticaPrestamo();
        mapToEntity(politicaPrestamoDTO, politicaPrestamo);
        return politicaPrestamoRepository.save(politicaPrestamo).getId();
    }

    public void update(final Integer id, final PoliticaPrestamoDTO politicaPrestamoDTO) {
        final PoliticaPrestamo politicaPrestamo = politicaPrestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(politicaPrestamoDTO, politicaPrestamo);
        politicaPrestamoRepository.save(politicaPrestamo);
    }

    public void delete(final Integer id) {
        politicaPrestamoRepository.deleteById(id);
    }

    private PoliticaPrestamoDTO mapToDTO(final PoliticaPrestamo politicaPrestamo,
            final PoliticaPrestamoDTO politicaPrestamoDTO) {
        politicaPrestamoDTO.setId(politicaPrestamo.getId());
        politicaPrestamoDTO.setMaxDiasPrestamo(politicaPrestamo.getMaxDiasPrestamo());
        politicaPrestamoDTO.setMaxRenovaciones(politicaPrestamo.getMaxRenovaciones());
        politicaPrestamoDTO.setBiblioteca(politicaPrestamo.getBiblioteca() == null ? null : politicaPrestamo.getBiblioteca().getId());
        politicaPrestamoDTO.setPoliticaPrestamoUsuarioUsuarios(politicaPrestamo.getPoliticaPrestamoUsuarioUsuarios().stream()
                .map(usuario -> usuario.getId())
                .toList());
        return politicaPrestamoDTO;
    }

    private PoliticaPrestamo mapToEntity(final PoliticaPrestamoDTO politicaPrestamoDTO,
            final PoliticaPrestamo politicaPrestamo) {
        politicaPrestamo.setMaxDiasPrestamo(politicaPrestamoDTO.getMaxDiasPrestamo());
        politicaPrestamo.setMaxRenovaciones(politicaPrestamoDTO.getMaxRenovaciones());
        final Biblioteca biblioteca = politicaPrestamoDTO.getBiblioteca() == null ? null : bibliotecaRepository.findById(politicaPrestamoDTO.getBiblioteca())
                .orElseThrow(() -> new NotFoundException("biblioteca not found"));
        politicaPrestamo.setBiblioteca(biblioteca);
        final List<Usuario> politicaPrestamoUsuarioUsuarios = usuarioRepository.findAllById(
                politicaPrestamoDTO.getPoliticaPrestamoUsuarioUsuarios() == null ? Collections.emptyList() : politicaPrestamoDTO.getPoliticaPrestamoUsuarioUsuarios());
        if (politicaPrestamoUsuarioUsuarios.size() != (politicaPrestamoDTO.getPoliticaPrestamoUsuarioUsuarios() == null ? 0 : politicaPrestamoDTO.getPoliticaPrestamoUsuarioUsuarios().size())) {
            throw new NotFoundException("one of politicaPrestamoUsuarioUsuarios not found");
        }
        politicaPrestamo.setPoliticaPrestamoUsuarioUsuarios(politicaPrestamoUsuarioUsuarios.stream().collect(Collectors.toSet()));
        return politicaPrestamo;
    }

}
