package io.uax.biblioteca.service;

import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.domain.Usuario;
import io.uax.biblioteca.model.LectorDTO;
import io.uax.biblioteca.repos.LectorRepository;
import io.uax.biblioteca.repos.MultasRepository;
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
public class LectorService {

    private final LectorRepository lectorRepository;
    private final MultasRepository multasRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    public LectorService(final LectorRepository lectorRepository,
            final MultasRepository multasRepository, final UsuarioRepository usuarioRepository,
            final PrestamoRepository prestamoRepository) {
        this.lectorRepository = lectorRepository;
        this.multasRepository = multasRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LectorDTO> findAll() {
        final List<Lector> lectors = lectorRepository.findAll(Sort.by("id"));
        return lectors.stream()
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .toList();
    }

    public LectorDTO get(final Integer id) {
        return lectorRepository.findById(id)
                .map(lector -> mapToDTO(lector, new LectorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LectorDTO lectorDTO) {
        final Lector lector = new Lector();
        mapToEntity(lectorDTO, lector);
        return lectorRepository.save(lector).getId();
    }

    public void update(final Integer id, final LectorDTO lectorDTO) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lectorDTO, lector);
        lectorRepository.save(lector);
    }

    public void delete(final Integer id) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        multasRepository.findAllByMultasLectorLectors(lector)
                .forEach(multas -> multas.getMultasLectorLectors().remove(lector));
        lectorRepository.delete(lector);
    }

    private LectorDTO mapToDTO(final Lector lector, final LectorDTO lectorDTO) {
        lectorDTO.setId(lector.getId());
        lectorDTO.setNombre(lector.getNombre());
        lectorDTO.setDireccion(lector.getDireccion());
        lectorDTO.setTelefono(lector.getTelefono());
        lectorDTO.setMultas(lector.getMultas() == null ? null : lector.getMultas().getId());
        lectorDTO.setUsuario(lector.getUsuario() == null ? null : lector.getUsuario().getId());
        lectorDTO.setLectorPrestamoPrestamoes(lector.getLectorPrestamoPrestamoes().stream()
                .map(prestamo -> prestamo.getId())
                .toList());
        return lectorDTO;
    }

    private Lector mapToEntity(final LectorDTO lectorDTO, final Lector lector) {
        lector.setNombre(lectorDTO.getNombre());
        lector.setDireccion(lectorDTO.getDireccion());
        lector.setTelefono(lectorDTO.getTelefono());
        final Multas multas = lectorDTO.getMultas() == null ? null : multasRepository.findById(lectorDTO.getMultas())
                .orElseThrow(() -> new NotFoundException("multas not found"));
        lector.setMultas(multas);
        final Usuario usuario = lectorDTO.getUsuario() == null ? null : usuarioRepository.findById(lectorDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        lector.setUsuario(usuario);
        final List<Prestamo> lectorPrestamoPrestamoes = prestamoRepository.findAllById(
                lectorDTO.getLectorPrestamoPrestamoes() == null ? Collections.emptyList() : lectorDTO.getLectorPrestamoPrestamoes());
        if (lectorPrestamoPrestamoes.size() != (lectorDTO.getLectorPrestamoPrestamoes() == null ? 0 : lectorDTO.getLectorPrestamoPrestamoes().size())) {
            throw new NotFoundException("one of lectorPrestamoPrestamoes not found");
        }
        lector.setLectorPrestamoPrestamoes(lectorPrestamoPrestamoes.stream().collect(Collectors.toSet()));
        return lector;
    }

    public String getReferencedWarning(final Integer id) {
        final Lector lector = lectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Prestamo lectorPrestamo = prestamoRepository.findFirstByLector(lector);
        if (lectorPrestamo != null) {
            return WebUtils.getMessage("lector.prestamo.lector.referenced", lectorPrestamo.getId());
        }
        final Multas multasLectorLectorsMultas = multasRepository.findFirstByMultasLectorLectors(lector);
        if (multasLectorLectorsMultas != null) {
            return WebUtils.getMessage("lector.multas.multasLectorLectors.referenced", multasLectorLectorsMultas.getId());
        }
        return null;
    }

}
