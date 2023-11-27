package io.uax.biblioteca.biblioteca.domain;

import io.uax.biblioteca.administrador.domain.Admin;
import io.uax.biblioteca.bibliotecario.domain.Bibliotecario;
import io.uax.biblioteca.domain.Libro;
import io.uax.biblioteca.domain.PoliticaPrestamo;
import io.uax.biblioteca.domain.Usuario;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Biblioteca {

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

    @OneToMany(mappedBy = "biblioteca")
    private Set<Usuario> bibliotecaUsuarios;

    @OneToMany(mappedBy = "biblioteca")
    private Set<Libro> bibliotecaLibroes;

    @OneToMany(mappedBy = "biblioteca")
    private Set<PoliticaPrestamo> bibliotecaPoliticaPrestamoes;

    @ManyToMany
    @JoinTable(
            name = "BibliotecaUsuario",
            joinColumns = @JoinColumn(name = "bibliotecaId"),
            inverseJoinColumns = @JoinColumn(name = "usuarioId")
    )
    private Set<Usuario> bibliotecaUsuarioUsuarios;

    @ManyToMany(mappedBy = "adminBibliotecaBibliotecas")
    private Set<Admin> adminBibliotecaAdmins;

    @ManyToMany(mappedBy = "bibliotecarioBibliotecaBibliotecas")
    private Set<Bibliotecario> bibliotecarioBibliotecaBibliotecarios;

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

    public Set<Usuario> getBibliotecaUsuarios() {
        return bibliotecaUsuarios;
    }

    public void setBibliotecaUsuarios(final Set<Usuario> bibliotecaUsuarios) {
        this.bibliotecaUsuarios = bibliotecaUsuarios;
    }

    public Set<Libro> getBibliotecaLibroes() {
        return bibliotecaLibroes;
    }

    public void setBibliotecaLibroes(final Set<Libro> bibliotecaLibroes) {
        this.bibliotecaLibroes = bibliotecaLibroes;
    }

    public Set<PoliticaPrestamo> getBibliotecaPoliticaPrestamoes() {
        return bibliotecaPoliticaPrestamoes;
    }

    public void setBibliotecaPoliticaPrestamoes(
            final Set<PoliticaPrestamo> bibliotecaPoliticaPrestamoes) {
        this.bibliotecaPoliticaPrestamoes = bibliotecaPoliticaPrestamoes;
    }

    public Set<Usuario> getBibliotecaUsuarioUsuarios() {
        return bibliotecaUsuarioUsuarios;
    }

    public void setBibliotecaUsuarioUsuarios(final Set<Usuario> bibliotecaUsuarioUsuarios) {
        this.bibliotecaUsuarioUsuarios = bibliotecaUsuarioUsuarios;
    }

    public Set<Admin> getAdminBibliotecaAdmins() {
        return adminBibliotecaAdmins;
    }

    public void setAdminBibliotecaAdmins(final Set<Admin> adminBibliotecaAdmins) {
        this.adminBibliotecaAdmins = adminBibliotecaAdmins;
    }

    public Set<Bibliotecario> getBibliotecarioBibliotecaBibliotecarios() {
        return bibliotecarioBibliotecaBibliotecarios;
    }

    public void setBibliotecarioBibliotecaBibliotecarios(
            final Set<Bibliotecario> bibliotecarioBibliotecaBibliotecarios) {
        this.bibliotecarioBibliotecaBibliotecarios = bibliotecarioBibliotecaBibliotecarios;
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
