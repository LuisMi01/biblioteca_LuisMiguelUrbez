package io.uax.biblioteca.service;

import io.uax.biblioteca.domain.Lector;
import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.model.MultasDTO;
import io.uax.biblioteca.repos.LectorRepository;
import io.uax.biblioteca.repos.MultasRepository;
import io.uax.biblioteca.repos.PrestamoRepository;
import io.uax.biblioteca.util.NotFoundException;
import io.uax.biblioteca.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class MultasService {

    private final MultasRepository multasRepository;
    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;

    public MultasService(final MultasRepository multasRepository,
            final PrestamoRepository prestamoRepository, final LectorRepository lectorRepository) {
        this.multasRepository = multasRepository;
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
    }

    public List<MultasDTO> findAll() {
        final List<Multas> multases = multasRepository.findAll(Sort.by("id"));
        return multases.stream()
                .map(multas -> mapToDTO(multas, new MultasDTO()))
                .toList();
    }

    public MultasDTO get(final Integer id) {
        return multasRepository.findById(id)
                .map(multas -> mapToDTO(multas, new MultasDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MultasDTO multasDTO) {
        final Multas multas = new Multas();
        mapToEntity(multasDTO, multas);
        return multasRepository.save(multas).getId();
    }

    public void update(final Integer id, final MultasDTO multasDTO) {
        final Multas multas = multasRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(multasDTO, multas);
        multasRepository.save(multas);
    }

    public void delete(final Integer id) {
        multasRepository.deleteById(id);
    }

    private MultasDTO mapToDTO(final Multas multas, final MultasDTO multasDTO) {
        multasDTO.setId(multas.getId());
        multasDTO.setTotal(multas.getTotal());
        multasDTO.setPrestamo(multas.getPrestamo() == null ? null : multas.getPrestamo().getId());
        multasDTO.setNombreLector(multas.getNombreLector());
        multasDTO.setMultasLectorLectors(multas.getMultasLectorLectors().stream()
                .map(lector -> lector.getId())
                .toList());
        return multasDTO;
    }

    private Multas mapToEntity(final MultasDTO multasDTO, final Multas multas) {
        multas.setTotal(multasDTO.getTotal());
        final Prestamo prestamo = multasDTO.getPrestamo() == null ? null : prestamoRepository.findById(multasDTO.getPrestamo())
                .orElseThrow(() -> new NotFoundException("prestamo not found"));
        multas.setPrestamo(prestamo);
        final List<Lector> multasLectorLectors = lectorRepository.findAllById(
                multasDTO.getMultasLectorLectors() == null ? Collections.emptyList() : multasDTO.getMultasLectorLectors());
                multasDTO.setNombreLector(multas.getNombreLector());
        if (multasLectorLectors.size() != (multasDTO.getMultasLectorLectors() == null ? 0 : multasDTO.getMultasLectorLectors().size())) {
            throw new NotFoundException("one of multasLectorLectors not found");
        }

        multas.setMultasLectorLectors(new HashSet<>(multasLectorLectors));

        return multas;
    }

    public String getReferencedWarning(final Integer id) {
        final Multas multas = multasRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Lector multasLector = lectorRepository.findFirstByMultas(multas);
        if (multasLector != null) {
            return WebUtils.getMessage("multas.lector.multas.referenced", multasLector.getId());
        }
        return null;
    }

}
