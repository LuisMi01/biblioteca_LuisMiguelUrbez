package io.uax.biblioteca.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.uax.biblioteca.model.TransaccionLibroDTO;
import io.uax.biblioteca.service.TransaccionLibroService;
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
@RequestMapping(value = "/api/transaccionLibros", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionLibroResource {

    private final TransaccionLibroService transaccionLibroService;

    public TransaccionLibroResource(final TransaccionLibroService transaccionLibroService) {
        this.transaccionLibroService = transaccionLibroService;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionLibroDTO>> getAllTransaccionLibros() {
        return ResponseEntity.ok(transaccionLibroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionLibroDTO> getTransaccionLibro(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(transaccionLibroService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createTransaccionLibro(
            @RequestBody @Valid final TransaccionLibroDTO transaccionLibroDTO) {
        final Integer createdId = transaccionLibroService.create(transaccionLibroDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateTransaccionLibro(
            @PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final TransaccionLibroDTO transaccionLibroDTO) {
        transaccionLibroService.update(id, transaccionLibroDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTransaccionLibro(
            @PathVariable(name = "id") final Integer id) {
        transaccionLibroService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
