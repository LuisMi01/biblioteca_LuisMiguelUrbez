package io.uax.biblioteca.service;

import io.uax.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.domain.Bibliotecario;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.model.LibroDTO;
import io.uax.biblioteca.repos.BibliotecaRepository;
import io.uax.biblioteca.repos.BibliotecarioRepository;
import io.uax.biblioteca.repos.LibroRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
import io.uax.biblioteca.util.NotFoundException;
import io.uax.biblioteca.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final BibliotecaRepository bibliotecaRepository;
    private final BibliotecarioRepository bibliotecarioRepository;
    private final PrestamoRepository prestamoRepository;

    public LibroService(final LibroRepository libroRepository,
            final BibliotecaRepository bibliotecaRepository,
            final BibliotecarioRepository bibliotecarioRepository,
            final PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.bibliotecaRepository = bibliotecaRepository;
        this.bibliotecarioRepository = bibliotecarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LibroDTO> findAll() {
        final List<Libro> libroes = libroRepository.findAll(Sort.by("id"));
        return libroes.stream()
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .toList();
    }

    public LibroDTO get(final Integer id) {
        return libroRepository.findById(id)
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LibroDTO libroDTO) {
        final Libro libro = new Libro();
        mapToEntity(libroDTO, libro);
        return libroRepository.save(libro).getId();
    }

    public void update(final Integer id, final LibroDTO libroDTO) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroDTO, libro);
        libroRepository.save(libro);
    }

    public void delete(final Integer id) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        bibliotecarioRepository.findAllByBibliotecarioLibroLibroes(libro)
                .forEach(bibliotecario -> bibliotecario.getBibliotecarioLibroLibroes().remove(libro));
        prestamoRepository.findAllByPrestamoLibroLibroes(libro)
                .forEach(prestamo -> prestamo.getPrestamoLibroLibroes().remove(libro));
        libroRepository.delete(libro);
    }

    private LibroDTO mapToDTO(final Libro libro, final LibroDTO libroDTO) {
        libroDTO.setId(libro.getId());
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor());
        libroDTO.setEditorial(libro.getEditorial());
        libroDTO.setEjemplaresTotales(libro.getEjemplaresTotales());
        libroDTO.setEjemplaresDisponibles(libro.getEjemplaresDisponibles());
        libroDTO.setEstado(libro.getEstado());
        libroDTO.setBiblioteca(libro.getBiblioteca() == null ? null : libro.getBiblioteca().getId());
        return libroDTO;
    }

    private Libro mapToEntity(final LibroDTO libroDTO, final Libro libro) {
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setEditorial(libroDTO.getEditorial());
        libro.setEjemplaresTotales(libroDTO.getEjemplaresTotales());
        libro.setEjemplaresDisponibles(libroDTO.getEjemplaresDisponibles());
        libro.setEstado(libroDTO.getEstado());
        final Biblioteca biblioteca = libroDTO.getBiblioteca() == null ? null : bibliotecaRepository.findById(libroDTO.getBiblioteca())
                .orElseThrow(() -> new NotFoundException("biblioteca not found"));
        libro.setBiblioteca(biblioteca);
        return libro;
    }

    public String getReferencedWarning(final Integer id) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo libroPrestamo = prestamoRepository.findFirstByLibro(libro);
        if (libroPrestamo != null) {
            return WebUtils.getMessage("libro.prestamo.libro.referenced", libroPrestamo.getId());
        }
        final Bibliotecario bibliotecarioLibroLibroesBibliotecario = bibliotecarioRepository.findFirstByBibliotecarioLibroLibroes(libro);
        if (bibliotecarioLibroLibroesBibliotecario != null) {
            return WebUtils.getMessage("libro.bibliotecario.bibliotecarioLibroLibroes.referenced", bibliotecarioLibroLibroesBibliotecario.getId());
        }
        final Prestamo prestamoLibroLibroesPrestamo = prestamoRepository.findFirstByPrestamoLibroLibroes(libro);
        if (prestamoLibroLibroesPrestamo != null) {
            return WebUtils.getMessage("libro.prestamo.prestamoLibroLibroes.referenced", prestamoLibroLibroesPrestamo.getId());
        }
        return null;
    }

}
