package com.webtrack.model;

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
 * Entidad que representa la relación entre una provincia y una modalidad de trabajo.
 * Esta entidad permite gestionar qué modalidades están disponibles en cada provincia,
 * siendo clave para la lógica de negocio en la asignación de puestos presenciales, remotos o híbridos
 */

@Entity
@Table(name = "modalidades_provincia") 
public class ModalidadProvincia {

	// Identificador único de la relación provincia-modalidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provincia_id", nullable = false) // Clave foránea hacia Provincia
    private Provincia provincia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modalidad_id", nullable = false) // Clave foránea hacia Modalidad
    private Modalidad modalidad;

    // Constructor vacío
    public ModalidadProvincia() {}

    // Constructor con argumentos
    public ModalidadProvincia(Provincia provincia, Modalidad modalidad) {
        this.provincia = provincia;
        this.modalidad = modalidad;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public String toString() {
        return "ModalidadProvincia{" +
                "id=" + id +
                ", provincia=" + (provincia != null ? provincia.getNombre() : null) +
                ", modalidad=" + (modalidad != null ? modalidad.getNombre() : null) +
                '}';
    }
}
