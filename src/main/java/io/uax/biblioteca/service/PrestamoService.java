package io.uax.biblioteca.service;

import io.uax.biblioteca.bibliotecario.domain.Bibliotecario;
import io.uax.biblioteca.lector.domain.Lector;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.model.PrestamoDTO;
import io.uax.biblioteca.repos.BibliotecarioRepository;
import io.uax.biblioteca.repos.LectorRepository;
import io.uax.biblioteca.repos.LibroRepository;
import io.uax.biblioteca.repos.MultasRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
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
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final LectorRepository lectorRepository;
    private final BibliotecarioRepository bibliotecarioRepository;
    private final MultasRepository multasRepository;

    public PrestamoService(final PrestamoRepository prestamoRepository,
            final LibroRepository libroRepository, final LectorRepository lectorRepository,
            final BibliotecarioRepository bibliotecarioRepository,
            final MultasRepository multasRepository) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.lectorRepository = lectorRepository;
        this.bibliotecarioRepository = bibliotecarioRepository;
        this.multasRepository = multasRepository;
    }

    public List<PrestamoDTO> findAll() {
        final List<Prestamo> prestamoes = prestamoRepository.findAll(Sort.by("id"));
        return prestamoes.stream()
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .toList();
    }

    public PrestamoDTO get(final Integer id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = new Prestamo();
        mapToEntity(prestamoDTO, prestamo);
        return prestamoRepository.save(prestamo).getId();
    }

    public void update(final Integer id, final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(prestamoDTO, prestamo);
        prestamoRepository.save(prestamo);
    }

    public void delete(final Integer id) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        lectorRepository.findAllByLectorPrestamoPrestamoes(prestamo)
                .forEach(lector -> lector.getLectorPrestamoPrestamoes().remove(prestamo));
        prestamoRepository.delete(prestamo);
    }

    private PrestamoDTO mapToDTO(final Prestamo prestamo, final PrestamoDTO prestamoDTO) {
        prestamoDTO.setId(prestamo.getId());
        prestamoDTO.setFechaPrestamo(prestamo.getFechaPrestamo());
        prestamoDTO.setFechaDevolucion(prestamo.getFechaDevolucion());
        prestamoDTO.setRenovado(prestamo.getRenovado());
        prestamoDTO.setEstadoPreestamo(prestamo.getEstadoPreestamo());
        prestamoDTO.setLibro(prestamo.getLibro() == null ? null : prestamo.getLibro().getId());
        prestamoDTO.setLector(prestamo.getLector() == null ? null : prestamo.getLector().getId());
        prestamoDTO.setBibliotecario(prestamo.getBibliotecario() == null ? null : prestamo.getBibliotecario().getId());
        prestamoDTO.setPrestamoLibroLibroes(prestamo.getPrestamoLibroLibroes().stream()
                .map(libro -> libro.getId())
                .toList());
        return prestamoDTO;
    }

    private Prestamo mapToEntity(final PrestamoDTO prestamoDTO, final Prestamo prestamo) {
        prestamo.setFechaPrestamo(prestamoDTO.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoDTO.getFechaDevolucion());
        prestamo.setRenovado(prestamoDTO.getRenovado());
        prestamo.setEstadoPreestamo(prestamoDTO.getEstadoPreestamo());
        final Libro libro = prestamoDTO.getLibro() == null ? null : libroRepository.findById(prestamoDTO.getLibro())
                .orElseThrow(() -> new NotFoundException("libro not found"));
        prestamo.setLibro(libro);
        final Lector lector = prestamoDTO.getLector() == null ? null : lectorRepository.findById(prestamoDTO.getLector())
                .orElseThrow(() -> new NotFoundException("lector not found"));
        prestamo.setLector(lector);
        final Bibliotecario bibliotecario = prestamoDTO.getBibliotecario() == null ? null : bibliotecarioRepository.findById(prestamoDTO.getBibliotecario())
                .orElseThrow(() -> new NotFoundException("bibliotecario not found"));
        prestamo.setBibliotecario(bibliotecario);
        final List<Libro> prestamoLibroLibroes = libroRepository.findAllById(
                prestamoDTO.getPrestamoLibroLibroes() == null ? Collections.emptyList() : prestamoDTO.getPrestamoLibroLibroes());
        if (prestamoLibroLibroes.size() != (prestamoDTO.getPrestamoLibroLibroes() == null ? 0 : prestamoDTO.getPrestamoLibroLibroes().size())) {
            throw new NotFoundException("one of prestamoLibroLibroes not found");
        }
        prestamo.setPrestamoLibroLibroes(prestamoLibroLibroes.stream().collect(Collectors.toSet()));
        return prestamo;
    }

    public String getReferencedWarning(final Integer id) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Multas prestamoMultas = multasRepository.findFirstByPrestamo(prestamo);
        if (prestamoMultas != null) {
            return WebUtils.getMessage("prestamo.multas.prestamo.referenced", prestamoMultas.getId());
        }
        final Lector lectorPrestamoPrestamoesLector = lectorRepository.findFirstByLectorPrestamoPrestamoes(prestamo);
        if (lectorPrestamoPrestamoesLector != null) {
            return WebUtils.getMessage("prestamo.lector.lectorPrestamoPrestamoes.referenced", lectorPrestamoPrestamoesLector.getId());
        }
        return null;
    }

}
