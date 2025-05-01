package com.webtrack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa una modalidad de trabajo en el sistema.
 * Las modalidades permiten definir cómo se desarrolla el trabajo: remoto, presencial, híbrido.
 */

@Entity
@Table(name = "modalidades")
public class Modalidad {

	// Identificador único de la modalidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @NotEmpty // Validación: el nombre no puede estar vacío
    @Size(max = 100) // Limita el tamaño máximo del nombre
    @Column(name = "nombre", nullable = false, unique = true) // Nombre obligatorio y único
    private String nombre;

    // Constructor vacío
    public Modalidad() {
    }

    // Constructor con argumentos
    public Modalidad(String nombre) {
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
        return "Modalidades{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
