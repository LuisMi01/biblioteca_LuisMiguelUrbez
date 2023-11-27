package io.uax.biblioteca.service;

import io.uax.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.domain.Bibliotecario;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.BibliotecarioDTO;
import io.uax.biblioteca.repos.BibliotecaRepository;
import io.uax.biblioteca.repos.BibliotecarioRepository;
import io.uax.biblioteca.repos.LibroRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
import io.uax.biblioteca.repos.UsuarioRepository;
import io.uax.biblioteca.util.NotFoundException;
import io.uax.biblioteca.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class BibliotecarioService {

    private final BibliotecarioRepository bibliotecarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final BibliotecaRepository bibliotecaRepository;
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public BibliotecarioService(final BibliotecarioRepository bibliotecarioRepository,
            final UsuarioRepository usuarioRepository,
            final BibliotecaRepository bibliotecaRepository, final LibroRepository libroRepository,
            final PrestamoRepository prestamoRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.bibliotecaRepository = bibliotecaRepository;
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<BibliotecarioDTO> findAll() {
        final List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findAll(Sort.by("id"));
        return bibliotecarios.stream()
                .map(bibliotecario -> mapToDTO(bibliotecario, new BibliotecarioDTO()))
                .toList();
    }

    public BibliotecarioDTO get(final Integer id) {
        return bibliotecarioRepository.findById(id)
                .map(bibliotecario -> mapToDTO(bibliotecario, new BibliotecarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BibliotecarioDTO bibliotecarioDTO) {
        final Bibliotecario bibliotecario = new Bibliotecario();
        mapToEntity(bibliotecarioDTO, bibliotecario);
        return bibliotecarioRepository.save(bibliotecario).getId();
    }

    public void update(final Integer id, final BibliotecarioDTO bibliotecarioDTO) {
        final Bibliotecario bibliotecario = bibliotecarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bibliotecarioDTO, bibliotecario);
        bibliotecarioRepository.save(bibliotecario);
    }

    public void delete(final Integer id) {
        bibliotecarioRepository.deleteById(id);
    }

    private BibliotecarioDTO mapToDTO(final Bibliotecario bibliotecario,
            final BibliotecarioDTO bibliotecarioDTO) {
        bibliotecarioDTO.setId(bibliotecario.getId());
        bibliotecarioDTO.setNombre(bibliotecario.getNombre());
        bibliotecarioDTO.setUsuario(bibliotecario.getUsuario() == null ? null : bibliotecario.getUsuario().getId());
        bibliotecarioDTO.setBibliotecarioBibliotecaBibliotecas(bibliotecario.getBibliotecarioBibliotecaBibliotecas().stream()
                .map(biblioteca -> biblioteca.getId())
                .toList());
        bibliotecarioDTO.setBibliotecarioLibroLibroes(bibliotecario.getBibliotecarioLibroLibroes().stream()
                .map(libro -> libro.getId())
                .toList());
        return bibliotecarioDTO;
    }

    private Bibliotecario mapToEntity(final BibliotecarioDTO bibliotecarioDTO,
            final Bibliotecario bibliotecario) {
        bibliotecario.setNombre(bibliotecarioDTO.getNombre());
        final Usuario usuario = bibliotecarioDTO.getUsuario() == null ? null : usuarioRepository.findById(bibliotecarioDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        bibliotecario.setUsuario(usuario);
        final List<Biblioteca> bibliotecarioBibliotecaBibliotecas = bibliotecaRepository.findAllById(
                bibliotecarioDTO.getBibliotecarioBibliotecaBibliotecas() == null ? Collections.emptyList() : bibliotecarioDTO.getBibliotecarioBibliotecaBibliotecas());
        if (bibliotecarioBibliotecaBibliotecas.size() != (bibliotecarioDTO.getBibliotecarioBibliotecaBibliotecas() == null ? 0 : bibliotecarioDTO.getBibliotecarioBibliotecaBibliotecas().size())) {
            throw new NotFoundException("one of bibliotecarioBibliotecaBibliotecas not found");
        }
        bibliotecario.setBibliotecarioBibliotecaBibliotecas(bibliotecarioBibliotecaBibliotecas.stream().collect(Collectors.toSet()));
        final List<Libro> bibliotecarioLibroLibroes = libroRepository.findAllById(
                bibliotecarioDTO.getBibliotecarioLibroLibroes() == null ? Collections.emptyList() : bibliotecarioDTO.getBibliotecarioLibroLibroes());
        if (bibliotecarioLibroLibroes.size() != (bibliotecarioDTO.getBibliotecarioLibroLibroes() == null ? 0 : bibliotecarioDTO.getBibliotecarioLibroLibroes().size())) {
            throw new NotFoundException("one of bibliotecarioLibroLibroes not found");
        }
        bibliotecario.setBibliotecarioLibroLibroes(bibliotecarioLibroLibroes.stream().collect(Collectors.toSet()));
        return bibliotecario;
    }

    public String getReferencedWarning(final Integer id) {
        final Bibliotecario bibliotecario = bibliotecarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo bibliotecarioPrestamo = prestamoRepository.findFirstByBibliotecario(bibliotecario);
        if (bibliotecarioPrestamo != null) {
            return WebUtils.getMessage("bibliotecario.prestamo.bibliotecario.referenced", bibliotecarioPrestamo.getId());
        }
        return null;
    }

}
