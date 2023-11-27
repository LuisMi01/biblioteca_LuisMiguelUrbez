package io.uax.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class TransaccionLibroDTO {

    private Integer id;

    @NotNull
    private LocalDate fechaTransaccion;

    @NotNull
    @Size(max = 255)
    private String accion;

    @NotNull
    @Size(max = 255)
    private String detalles;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDate getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(final LocalDate fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(final String accion) {
        this.accion = accion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(final String detalles) {
        this.detalles = detalles;
    }

}
