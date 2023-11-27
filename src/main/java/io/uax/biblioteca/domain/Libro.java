package io.uax.biblioteca.domain;

import io.uax.biblioteca.model.EstadoLibro;
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
public class Libro {

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
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String editorial;

    @Column(nullable = false)
    private Integer ejemplaresTotales;

    @Column(nullable = false)
    private Integer ejemplaresDisponibles;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoLibro estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biblioteca_id")
    private Biblioteca biblioteca;

    @OneToMany(mappedBy = "libro")
    private Set<Prestamo> libroPrestamoes;

    @ManyToMany(mappedBy = "bibliotecarioLibroLibroes")
    private Set<Bibliotecario> bibliotecarioLibroBibliotecarios;

    @ManyToMany(mappedBy = "prestamoLibroLibroes")
    private Set<Prestamo> prestamoLibroPrestamoes;

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

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(final Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Set<Prestamo> getLibroPrestamoes() {
        return libroPrestamoes;
    }

    public void setLibroPrestamoes(final Set<Prestamo> libroPrestamoes) {
        this.libroPrestamoes = libroPrestamoes;
    }

    public Set<Bibliotecario> getBibliotecarioLibroBibliotecarios() {
        return bibliotecarioLibroBibliotecarios;
    }

    public void setBibliotecarioLibroBibliotecarios(
            final Set<Bibliotecario> bibliotecarioLibroBibliotecarios) {
        this.bibliotecarioLibroBibliotecarios = bibliotecarioLibroBibliotecarios;
    }

    public Set<Prestamo> getPrestamoLibroPrestamoes() {
        return prestamoLibroPrestamoes;
    }

    public void setPrestamoLibroPrestamoes(final Set<Prestamo> prestamoLibroPrestamoes) {
        this.prestamoLibroPrestamoes = prestamoLibroPrestamoes;
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
