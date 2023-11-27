package io.uax.biblioteca.domain;

import io.uax.biblioteca.bibliotecario.domain.Bibliotecario;
import io.uax.biblioteca.lector.domain.Lector;
import io.uax.biblioteca.model.EstadoPrestamo;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Prestamo {

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
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    private LocalDate fechaDevolucion;

    @Column(nullable = false)
    private Boolean renovado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estadoPreestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lector_id")
    private Lector lector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliotecario_id")
    private Bibliotecario bibliotecario;

    @OneToMany(mappedBy = "prestamo")
    private Set<Multas> prestamoMultases;

    @ManyToMany(mappedBy = "lectorPrestamoPrestamoes")
    private Set<Lector> lectorPrestamoLectors;

    @ManyToMany
    @JoinTable(
            name = "PrestamoLibro",
            joinColumns = @JoinColumn(name = "prestamoId"),
            inverseJoinColumns = @JoinColumn(name = "libroId")
    )
    private Set<Libro> prestamoLibroLibroes;

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

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(final Libro libro) {
        this.libro = libro;
    }

    public Lector getLector() {
        return lector;
    }

    public void setLector(final Lector lector) {
        this.lector = lector;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(final Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Set<Multas> getPrestamoMultases() {
        return prestamoMultases;
    }

    public void setPrestamoMultases(final Set<Multas> prestamoMultases) {
        this.prestamoMultases = prestamoMultases;
    }

    public Set<Lector> getLectorPrestamoLectors() {
        return lectorPrestamoLectors;
    }

    public void setLectorPrestamoLectors(final Set<Lector> lectorPrestamoLectors) {
        this.lectorPrestamoLectors = lectorPrestamoLectors;
    }

    public Set<Libro> getPrestamoLibroLibroes() {
        return prestamoLibroLibroes;
    }

    public void setPrestamoLibroLibroes(final Set<Libro> prestamoLibroLibroes) {
        this.prestamoLibroLibroes = prestamoLibroLibroes;
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
