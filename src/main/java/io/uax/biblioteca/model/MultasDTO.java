package io.uax.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


public class MultasDTO {

    private Integer id;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "32.08")
    private BigDecimal total;

    private String prestamo;

    private String nombreLector;



    public String getNombreLector() {
        return nombreLector;
    }

    public void setNombreLector(String nombreLector) {
        this.nombreLector = nombreLector;
    }

    private List<Integer> multasLectorLectors;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public String getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(final String prestamo) {
        this.prestamo = prestamo;
    }
    public List<Integer> getMultasLectorLectors() {
        return multasLectorLectors;
    }

    public void setMultasLectorLectors(final List<Integer> multasLectorLectors) {
        this.multasLectorLectors = multasLectorLectors;
    }


}
