package io.uax.biblioteca.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.uax.biblioteca.model.BibliotecarioDTO;
import io.uax.biblioteca.service.BibliotecarioService;
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
@RequestMapping(value = "/api/bibliotecarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class BibliotecarioResource {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioResource(final BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @GetMapping
    public ResponseEntity<List<BibliotecarioDTO>> getAllBibliotecarios() {
        return ResponseEntity.ok(bibliotecarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecarioDTO> getBibliotecario(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(bibliotecarioService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createBibliotecario(
            @RequestBody @Valid final BibliotecarioDTO bibliotecarioDTO) {
        final Integer createdId = bibliotecarioService.create(bibliotecarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateBibliotecario(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final BibliotecarioDTO bibliotecarioDTO) {
        bibliotecarioService.update(id, bibliotecarioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBibliotecario(@PathVariable(name = "id") final Integer id) {
        bibliotecarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
