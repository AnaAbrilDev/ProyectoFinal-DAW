package com.webtrack.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa el historial de cambios de estado de una candidatura.
 * Cada vez que una candidatura cambia de estado, se genera un registro en esta tabla,
 * permitiendo realizar seguimientos del proceso de selección.
 */

@Entity
@Table(name = "historial_estado_candidatura") // Tabla para guardar el histórico de cambios de estado
public class HistorialEstadoCandidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Clave primaria
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidatura_id", nullable = false) // Relación con candidatura
    private Candidatura candidatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false) // Relación con estado
    private EstadoCandidatura estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true) // Relación con usuario que realizó el cambio
    private Usuario usuario;

    @Column(name = "fecha_cambio", nullable = false, updatable = false) // Fecha del cambio
    private LocalDateTime fechaCambio;

    // Constructor vacío
    public HistorialEstadoCandidatura() {}

    // Constructor con argumentos
    public HistorialEstadoCandidatura(Candidatura candidatura, EstadoCandidatura estado, Usuario usuario) {
        this.candidatura = candidatura;
        this.estado = estado;
        this.usuario = usuario;
        this.fechaCambio = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidatura getCandidatura() {
        return candidatura;
    }

    public void setCandidatura(Candidatura candidatura) {
        this.candidatura = candidatura;
    }

    public EstadoCandidatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoCandidatura estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    @Override
    public String toString() {
        return "HistorialEstadoCandidatura{" +
                "id=" + id +
                ", candidatura=" + (candidatura != null ? candidatura.getId() : null) +
                ", estado=" + (estado != null ? estado.getNombre() : null) +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", fechaCambio=" + fechaCambio +
                '}';
    }
}
