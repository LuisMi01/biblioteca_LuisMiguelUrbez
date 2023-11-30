package io.uax.biblioteca.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


public class LectorDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String direccion;

    @NotNull
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener exactamente 9 dígitos.")
    private String telefono;

    private Integer multas;

    private Integer usuario;

    private List<Integer> lectorPrestamoPrestamoes;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(final String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }

    public Integer getMultas() {
        return multas;
    }

    public void setMultas(final Integer multas) {
        this.multas = multas;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(final Integer usuario) {
        this.usuario = usuario;
    }

    public List<Integer> getLectorPrestamoPrestamoes() {
        return lectorPrestamoPrestamoes;
    }

    public void setLectorPrestamoPrestamoes(final List<Integer> lectorPrestamoPrestamoes) {
        this.lectorPrestamoPrestamoes = lectorPrestamoPrestamoes;
    }

}
