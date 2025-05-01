package com.webtrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

/**
 * Entidad que representa el estado de una candidatura en el sistema.
 * Esta clase mapea la tabla "estados_candidatura" en la base de datos, almacenando los
 * diferentes estados disponibles (por ejemplo, "Pendiente", "Aceptado" o "Rechazado").
 * 
 * Los estados se utilizan para indicar el progreso de una candidatura en el proceso de selección.
 */

@Entity
@Table(name = "estados_candidatura") 
public class EstadoCandidatura {

	// Identificador único del estado de candidatura
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @NotEmpty
    @Column(name = "nombre", nullable = false, unique = true, length = 50) 
    private String nombre;

    // Constructor vacío
    public EstadoCandidatura() {}

    // Constructor con argumentos
    public EstadoCandidatura(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    @Override
    public String toString() {
        return "EstadoCandidatura{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
