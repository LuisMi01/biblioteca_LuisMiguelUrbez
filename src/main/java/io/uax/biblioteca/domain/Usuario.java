package io.uax.biblioteca.domain;

import io.uax.biblioteca.administrador.domain.Admin;
import io.uax.biblioteca.biblioteca.domain.Biblioteca;
import io.uax.biblioteca.bibliotecario.domain.Bibliotecario;
import io.uax.biblioteca.lector.domain.Lector;
import io.uax.biblioteca.model.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biblioteca_id")
    private Biblioteca biblioteca;

    @OneToMany(mappedBy = "usuario")
    private Set<Bibliotecario> usuarioBibliotecarios;

    @OneToMany(mappedBy = "usuario")
    private Set<Lector> usuarioLectors;

    @OneToMany(mappedBy = "usuario")
    private Set<Admin> usuarioAdmins;

    @ManyToMany(mappedBy = "bibliotecaUsuarioUsuarios")
    private Set<Biblioteca> bibliotecaUsuarioBibliotecas;

    @ManyToMany(mappedBy = "politicaPrestamoUsuarioUsuarios")
    private Set<PoliticaPrestamo> politicaPrestamoUsuarioPoliticaPrestamoes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(final Rol rol) {
        this.rol = rol;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(final Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Set<Bibliotecario> getUsuarioBibliotecarios() {
        return usuarioBibliotecarios;
    }

    public void setUsuarioBibliotecarios(final Set<Bibliotecario> usuarioBibliotecarios) {
        this.usuarioBibliotecarios = usuarioBibliotecarios;
    }

    public Set<Lector> getUsuarioLectors() {
        return usuarioLectors;
    }

    public void setUsuarioLectors(final Set<Lector> usuarioLectors) {
        this.usuarioLectors = usuarioLectors;
    }

    public Set<Admin> getUsuarioAdmins() {
        return usuarioAdmins;
    }

    public void setUsuarioAdmins(final Set<Admin> usuarioAdmins) {
        this.usuarioAdmins = usuarioAdmins;
    }

    public Set<Biblioteca> getBibliotecaUsuarioBibliotecas() {
        return bibliotecaUsuarioBibliotecas;
    }

    public void setBibliotecaUsuarioBibliotecas(
            final Set<Biblioteca> bibliotecaUsuarioBibliotecas) {
        this.bibliotecaUsuarioBibliotecas = bibliotecaUsuarioBibliotecas;
    }

    public Set<PoliticaPrestamo> getPoliticaPrestamoUsuarioPoliticaPrestamoes() {
        return politicaPrestamoUsuarioPoliticaPrestamoes;
    }

    public void setPoliticaPrestamoUsuarioPoliticaPrestamoes(
            final Set<PoliticaPrestamo> politicaPrestamoUsuarioPoliticaPrestamoes) {
        this.politicaPrestamoUsuarioPoliticaPrestamoes = politicaPrestamoUsuarioPoliticaPrestamoes;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
