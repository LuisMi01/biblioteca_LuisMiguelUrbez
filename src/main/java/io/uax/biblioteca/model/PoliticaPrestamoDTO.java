package io.uax.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;


public class PoliticaPrestamoDTO {

    private Integer id;

    @NotNull
    private Integer maxDiasPrestamo;

    @NotNull
    private Integer maxRenovaciones;

    private Integer biblioteca;

    private List<Integer> politicaPrestamoUsuarioUsuarios;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getMaxDiasPrestamo() {
        return maxDiasPrestamo;
    }

    public void setMaxDiasPrestamo(final Integer maxDiasPrestamo) {
        this.maxDiasPrestamo = maxDiasPrestamo;
    }

    public Integer getMaxRenovaciones() {
        return maxRenovaciones;
    }

    public void setMaxRenovaciones(final Integer maxRenovaciones) {
        this.maxRenovaciones = maxRenovaciones;
    }

    public Integer getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(final Integer biblioteca) {
        this.biblioteca = biblioteca;
    }

    public List<Integer> getPoliticaPrestamoUsuarioUsuarios() {
        return politicaPrestamoUsuarioUsuarios;
    }

    public void setPoliticaPrestamoUsuarioUsuarios(
            final List<Integer> politicaPrestamoUsuarioUsuarios) {
        this.politicaPrestamoUsuarioUsuarios = politicaPrestamoUsuarioUsuarios;
    }

}
