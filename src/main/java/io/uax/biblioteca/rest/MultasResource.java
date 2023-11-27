package io.uax.biblioteca.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.uax.biblioteca.model.MultasDTO;
import io.uax.biblioteca.service.MultasService;
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
@RequestMapping(value = "/api/multass", produces = MediaType.APPLICATION_JSON_VALUE)
public class MultasResource {

    private final MultasService multasService;

    public MultasResource(final MultasService multasService) {
        this.multasService = multasService;
    }

    @GetMapping
    public ResponseEntity<List<MultasDTO>> getAllMultass() {
        return ResponseEntity.ok(multasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MultasDTO> getMultas(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(multasService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMultas(@RequestBody @Valid final MultasDTO multasDTO) {
        final Integer createdId = multasService.create(multasDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMultas(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MultasDTO multasDTO) {
        multasService.update(id, multasDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMultas(@PathVariable(name = "id") final Integer id) {
        multasService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
