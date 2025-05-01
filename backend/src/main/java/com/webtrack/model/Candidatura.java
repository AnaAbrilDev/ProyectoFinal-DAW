package com.webtrack.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

/**
 * Entidad que representa una candidatura en el sistema.
 * Una candidatura es la asociación entre un candidato y un proceso de selección,
 * con un historial de cambios en su estado
 */
@Entity
@Table(name = "candidaturas")
public class Candidatura {
	
	// Clave primaria de la entidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relación muchos-a-uno con la entidad Candidato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    // Relación muchos-a-uno con la entidad ProcesoSeleccion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proceso_id", nullable = false)
    private ProcesoSeleccion procesoSeleccion;
    
    /**
     * Fecha en la que se creó la candidatura.
     * Se establece automáticamente al crear la entidad y no puede ser modificada posteriormente.
     */
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // Fecha de la última vez que la candidatura fue actualizada
    @Column(name = "ultima_actualizacion", nullable = false)
    private LocalDateTime ultimaActualizacion;
    
    // Relación uno-a-muchos con el historial de estados de la candidatura
    @OneToMany(mappedBy = "candidatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistorialEstadoCandidatura> historialEstados = new HashSet<>();


 // Constructor vacío
    public Candidatura() {}

    // Constructor con argumentos
    public Candidatura(Candidato candidato, ProcesoSeleccion procesoSeleccion) {
        this.candidato = candidato;
        this.procesoSeleccion = procesoSeleccion;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public ProcesoSeleccion getProcesoSeleccion() {
        return procesoSeleccion;
    }

    public void setProcesoSeleccion(ProcesoSeleccion procesoSeleccion) {
        this.procesoSeleccion = procesoSeleccion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    @PreUpdate
    public void preUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Candidatura{" +
                "candidaturaID=" + id +
                ", candidato=" + (candidato != null ? candidato.getId() : null) +
                ", procesoSeleccion=" + (procesoSeleccion != null ? procesoSeleccion.getId() : null) +
                ", fechaCreacion=" + fechaCreacion +
                ", ultimaActualizacion=" + ultimaActualizacion +
                '}';
    }
}

