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
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class PoliticaPrestamo {

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
    private Integer maxDiasPrestamo;

    @Column(nullable = false)
    private Integer maxRenovaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biblioteca_id")
    private Biblioteca biblioteca;

    @ManyToMany
    @JoinTable(
            name = "PoliticaPrestamoUsuario",
            joinColumns = @JoinColumn(name = "politicaPrestamoId"),
            inverseJoinColumns = @JoinColumn(name = "usuarioId")
    )
    private Set<Usuario> politicaPrestamoUsuarioUsuarios;

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

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(final Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Set<Usuario> getPoliticaPrestamoUsuarioUsuarios() {
        return politicaPrestamoUsuarioUsuarios;
    }

    public void setPoliticaPrestamoUsuarioUsuarios(
            final Set<Usuario> politicaPrestamoUsuarioUsuarios) {
        this.politicaPrestamoUsuarioUsuarios = politicaPrestamoUsuarioUsuarios;
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
