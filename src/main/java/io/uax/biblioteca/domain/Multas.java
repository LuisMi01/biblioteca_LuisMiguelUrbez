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
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Multas {

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

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "multas")
    private Set<Lector> multasLectors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;

    @ManyToMany
    @JoinTable(
            name = "MultasLector",
            joinColumns = @JoinColumn(name = "multasId"),
            inverseJoinColumns = @JoinColumn(name = "lectorId")
    )
    private Set<Lector> multasLectorLectors;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public Set<Lector> getMultasLectors() {
        return multasLectors;
    }

    public void setMultasLectors(final Set<Lector> multasLectors) {
        this.multasLectors = multasLectors;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(final Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Set<Lector> getMultasLectorLectors() {
        return multasLectorLectors;
    }

    public void setMultasLectorLectors(final Set<Lector> multasLectorLectors) {
        this.multasLectorLectors = multasLectorLectors;
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
