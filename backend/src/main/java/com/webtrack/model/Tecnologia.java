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
 * Entidad que representa una tecnología en el sistema.
 * Cada tecnología se almacena en la tabla "tecnologias" en la base de datos.
 */

@Entity
@Table(name = "tecnologias") // Tabla que almacena las tecnologías
public class Tecnologia {

	// Identificador único de la tecnología
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @NotEmpty
    @Size(max = 100) // Limitamos la longitud del nombre de la tecnología
    @Column(name = "nombre", nullable = false, unique = true) // Nombre obligatorio y único
    private String nombre;


    // Constructor vacío
    public Tecnologia() {}

    // Constructor con argumentos
    public Tecnologia(String nombre) {
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
        return "Tecnologia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
