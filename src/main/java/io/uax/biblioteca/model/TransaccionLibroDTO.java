package io.uax.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TransaccionLibroDTO {

    private Integer id;

    @NotNull
    private String libro;

    @NotNull
    private String nombreLibro;

    @NotNull
    private EstadoLibro estado;

    //getters y setters

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(final String libro) {
        this.libro = libro;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(final String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(final EstadoLibro estado) {
        this.estado = estado;
    }


}
