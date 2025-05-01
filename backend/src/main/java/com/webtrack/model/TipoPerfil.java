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
 * Entidad que representa un tipo de perfil profesional en el sistema.
 * Cada tipo de perfil se almacena en la tabla "tipos_perfil" en la base de datos.
 */

@Entity
@Table(name = "tipos_perfil") // Tabla que almacena ls tipos de perfil
public class TipoPerfil {

	// Identificador único del tipo de perfil
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @NotEmpty
    @Size(max = 100) // Limita la longitud del título
    @Column(name = "nombre", nullable = false, unique = true) // Título obligatorio y único
    private String nombre;

    // Constructor vacío
    public TipoPerfil() {}

    // Constructor con argumentos
    public TipoPerfil(String nombre) {
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
        return "TipoPerfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
