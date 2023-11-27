package io.uax.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LibroDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String titulo;

    @NotNull
    @Size(max = 255)
    private String autor;

    @NotNull
    @Size(max = 255)
    private String editorial;

    @NotNull
    private Integer ejemplaresTotales;

    @NotNull
    private Integer ejemplaresDisponibles;

    @NotNull
    private EstadoLibro estado;

    private Integer biblioteca;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(final String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(final String editorial) {
        this.editorial = editorial;
    }

    public Integer getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    public void setEjemplaresTotales(final Integer ejemplaresTotales) {
        this.ejemplaresTotales = ejemplaresTotales;
    }

    public Integer getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public void setEjemplaresDisponibles(final Integer ejemplaresDisponibles) {
        this.ejemplaresDisponibles = ejemplaresDisponibles;
    }

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(final EstadoLibro estado) {
        this.estado = estado;
    }

    public Integer getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(final Integer biblioteca) {
        this.biblioteca = biblioteca;
    }

}
