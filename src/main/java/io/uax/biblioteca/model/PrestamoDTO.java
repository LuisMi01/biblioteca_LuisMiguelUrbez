package io.uax.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


public class PrestamoDTO {

    private Integer id;

    @NotNull
    private LocalDate fechaPrestamo;

    @NotNull
    private LocalDate fechaDevolucion;

    @NotNull
    private Boolean renovado;

    @NotNull
    private EstadoPrestamo estadoPreestamo;

    private Integer libro;

    private Integer lector;

    private Integer bibliotecario;

    private List<Integer> prestamoLibroLibroes;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(final LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(final LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Boolean getRenovado() {
        return renovado;
    }

    public void setRenovado(final Boolean renovado) {
        this.renovado = renovado;
    }

    public EstadoPrestamo getEstadoPreestamo() {
        return estadoPreestamo;
    }

    public void setEstadoPreestamo(final EstadoPrestamo estadoPreestamo) {
        this.estadoPreestamo = estadoPreestamo;
    }

    public Integer getLibro() {
        return libro;
    }

    public void setLibro(final Integer libro) {
        this.libro = libro;
    }

    public Integer getLector() {
        return lector;
    }

    public void setLector(final Integer lector) {
        this.lector = lector;
    }

    public Integer getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(final Integer bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public List<Integer> getPrestamoLibroLibroes() {
        return prestamoLibroLibroes;
    }

    public void setPrestamoLibroLibroes(final List<Integer> prestamoLibroLibroes) {
        this.prestamoLibroLibroes = prestamoLibroLibroes;
    }

}
