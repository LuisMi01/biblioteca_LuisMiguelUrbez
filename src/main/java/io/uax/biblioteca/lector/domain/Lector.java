package io.uax.biblioteca.lector.domain;

import io.uax.biblioteca.domain.Multas;
import io.uax.biblioteca.domain.Prestamo;
import io.uax.biblioteca.domain.Usuario;
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
public class Lector {

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
    private String direccion;

    @Column(nullable = false, length = 20)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multas_id")
    private Multas multas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "lector")
    private Set<Prestamo> lectorPrestamoes;

    @ManyToMany
    @JoinTable(
            name = "LectorPrestamo",
            joinColumns = @JoinColumn(name = "lectorId"),
            inverseJoinColumns = @JoinColumn(name = "prestamoId")
    )
    private Set<Prestamo> lectorPrestamoPrestamoes;

    @ManyToMany(mappedBy = "multasLectorLectors")
    private Set<Multas> multasLectorMultases;

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

    public Multas getMultas() {
        return multas;
    }

    public void setMultas(final Multas multas) {
        this.multas = multas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Prestamo> getLectorPrestamoes() {
        return lectorPrestamoes;
    }

    public void setLectorPrestamoes(final Set<Prestamo> lectorPrestamoes) {
        this.lectorPrestamoes = lectorPrestamoes;
    }

    public Set<Prestamo> getLectorPrestamoPrestamoes() {
        return lectorPrestamoPrestamoes;
    }

    public void setLectorPrestamoPrestamoes(final Set<Prestamo> lectorPrestamoPrestamoes) {
        this.lectorPrestamoPrestamoes = lectorPrestamoPrestamoes;
    }

    public Set<Multas> getMultasLectorMultases() {
        return multasLectorMultases;
    }

    public void setMultasLectorMultases(final Set<Multas> multasLectorMultases) {
        this.multasLectorMultases = multasLectorMultases;
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
