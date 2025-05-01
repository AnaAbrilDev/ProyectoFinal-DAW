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
 * Entidad que representa una provincia en el sistema.
 * Esta clase mapea la tabla "provincias" en la base de datos.
 */

@Entity
@Table(name = "provincias") // Tabla que almacena las provincias
public class Provincia {

	// Identificador único de la provincia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @NotEmpty // Validación: el nombre no puede estar vacío
    @Size(max = 100) // Limita el tamaño máximo del nombre
    @Column(name = "nombre", nullable = false, unique = true) // Nombre obligatorio y único
    private String nombre;

    // Constructor vacío
    public Provincia() {
    }

    // Constructor con argumentos
    public Provincia(String nombre) {
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
        return "Provincias{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
