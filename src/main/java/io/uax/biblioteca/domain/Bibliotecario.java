package io.uax.biblioteca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Bibliotecario {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "bibliotecario")
    private Set<Prestamo> bibliotecarioPrestamoes;

    @ManyToMany
    @JoinTable(
            name = "BibliotecarioBiblioteca",
            joinColumns = @JoinColumn(name = "bibliotecarioId"),
            inverseJoinColumns = @JoinColumn(name = "bibliotecaId")
    )
    private Set<Biblioteca> bibliotecarioBibliotecaBibliotecas;

    @ManyToMany
    @JoinTable(
            name = "BibliotecarioLibro",
            joinColumns = @JoinColumn(name = "bibliotecarioId"),
            inverseJoinColumns = @JoinColumn(name = "libroId")
    )
    private Set<Libro> bibliotecarioLibroLibroes;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Prestamo> getBibliotecarioPrestamoes() {
        return bibliotecarioPrestamoes;
    }

    public void setBibliotecarioPrestamoes(final Set<Prestamo> bibliotecarioPrestamoes) {
        this.bibliotecarioPrestamoes = bibliotecarioPrestamoes;
    }

    public Set<Biblioteca> getBibliotecarioBibliotecaBibliotecas() {
        return bibliotecarioBibliotecaBibliotecas;
    }

    public void setBibliotecarioBibliotecaBibliotecas(
            final Set<Biblioteca> bibliotecarioBibliotecaBibliotecas) {
        this.bibliotecarioBibliotecaBibliotecas = bibliotecarioBibliotecaBibliotecas;
    }

    public Set<Libro> getBibliotecarioLibroLibroes() {
        return bibliotecarioLibroLibroes;
    }

    public void setBibliotecarioLibroLibroes(final Set<Libro> bibliotecarioLibroLibroes) {
        this.bibliotecarioLibroLibroes = bibliotecarioLibroLibroes;
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
