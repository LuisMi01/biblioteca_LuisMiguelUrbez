package io.uax.biblioteca.bibliotecario.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


public class BibliotecarioDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    private Integer usuario;

    private List<Integer> bibliotecarioBibliotecaBibliotecas;

    private List<Integer> bibliotecarioLibroLibroes;

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

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(final Integer usuario) {
        this.usuario = usuario;
    }

    public List<Integer> getBibliotecarioBibliotecaBibliotecas() {
        return bibliotecarioBibliotecaBibliotecas;
    }

    public void setBibliotecarioBibliotecaBibliotecas(
            final List<Integer> bibliotecarioBibliotecaBibliotecas) {
        this.bibliotecarioBibliotecaBibliotecas = bibliotecarioBibliotecaBibliotecas;
    }

    public List<Integer> getBibliotecarioLibroLibroes() {
        return bibliotecarioLibroLibroes;
    }

    public void setBibliotecarioLibroLibroes(final List<Integer> bibliotecarioLibroLibroes) {
        this.bibliotecarioLibroLibroes = bibliotecarioLibroLibroes;
    }

}
