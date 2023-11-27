package io.uax.biblioteca.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.uax.biblioteca.model.PoliticaPrestamoDTO;
import io.uax.biblioteca.service.PoliticaPrestamoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/politicaPrestamos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoliticaPrestamoResource {

    private final PoliticaPrestamoService politicaPrestamoService;

    public PoliticaPrestamoResource(final PoliticaPrestamoService politicaPrestamoService) {
        this.politicaPrestamoService = politicaPrestamoService;
    }

    @GetMapping
    public ResponseEntity<List<PoliticaPrestamoDTO>> getAllPoliticaPrestamos() {
        return ResponseEntity.ok(politicaPrestamoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliticaPrestamoDTO> getPoliticaPrestamo(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(politicaPrestamoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPoliticaPrestamo(
            @RequestBody @Valid final PoliticaPrestamoDTO politicaPrestamoDTO) {
        final Integer createdId = politicaPrestamoService.create(politicaPrestamoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updatePoliticaPrestamo(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final PoliticaPrestamoDTO politicaPrestamoDTO) {
        politicaPrestamoService.update(id, politicaPrestamoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePoliticaPrestamo(
            @PathVariable(name = "id") final Integer id) {
        politicaPrestamoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
