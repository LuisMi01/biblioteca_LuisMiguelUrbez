package io.uax.biblioteca.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.uax.biblioteca.lector.model.LectorDTO;
import io.uax.biblioteca.lector.service.LectorService;
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
@RequestMapping(value = "/api/lectors", produces = MediaType.APPLICATION_JSON_VALUE)
public class LectorResource {

    private final LectorService lectorService;

    public LectorResource(final LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping
    public ResponseEntity<List<LectorDTO>> getAllLectors() {
        return ResponseEntity.ok(lectorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectorDTO> getLector(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(lectorService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createLector(@RequestBody @Valid final LectorDTO lectorDTO) {
        final Integer createdId = lectorService.create(lectorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateLector(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final LectorDTO lectorDTO) {
        lectorService.update(id, lectorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLector(@PathVariable(name = "id") final Integer id) {
        lectorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
