package com.webtrack.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

/**
 * Entidad que representa un proceso de selección en el sistema.
 * Un proceso de selección incluye información clave como el sector del proyecto,
 * su estado, prioridad e información adicional como el salario y fecha de creación.
 */

@Entity
@Table(name = "procesos_seleccion") 
public class ProcesoSeleccion {

	// Identificador único del proceso de selección
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Sector del proyecto asociado al proceso de selección
    @Column(nullable = false)
    private String sectorProyecto;

    /**
     * Estado del proceso de selección.
     * Puede ser: Activo, Logrado, En gestión, Cerrado
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstadoProceso estado;
    
    /**
     * Prioridad del proceso de selección.
     * Puede ser: Baja, Media, Alta
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Prioridad prioridad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modalidad_provincia_id", nullable = true)
    private ModalidadProvincia modalidadProvincia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_perfil_id", nullable = true)
    private TipoPerfil tipoPerfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = true)
    private Double salario;
    
    @Column(name = "fecha_creacion", nullable = false)
    private java.time.LocalDateTime fechaCreacion;

    // Relación Muchos a Muchos con Tecnología
    @ManyToMany
    @JoinTable(
        name = "procesos_tecnologias", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "proceso_id", referencedColumnName = "id"), // Clave foránea hacia ProcesoSeleccion
        inverseJoinColumns = @JoinColumn(name = "tecnologia_id", referencedColumnName = "id") // Clave foránea hacia Tecnologia
    )
    private Set<Tecnologia> tecnologias = new HashSet<>();
    
    @OneToMany(mappedBy = "procesoSeleccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Candidatura> candidaturas = new HashSet<>();

 // Constructor vacío
    public ProcesoSeleccion() {
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorProyecto() {
        return sectorProyecto;
    }

    public void setSectorProyecto(String sectorProyecto) {
        this.sectorProyecto = sectorProyecto;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }
    
    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }


    public ModalidadProvincia getModalidadProvincia() {
        return modalidadProvincia;
    }

    public void setModalidadProvincia(ModalidadProvincia modalidadProvincia) {
        this.modalidadProvincia = modalidadProvincia;
    }

    public TipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(TipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }
    
    public java.time.LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.time.LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Tecnologia> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(Set<Tecnologia> tecnologias) {
        this.tecnologias = tecnologias;
    }
    
    // Enumeraciones
    
    public enum EstadoProceso  {
    	ACTIVO, LOGRADO, EN_GESTION, CERRADO
    }
    
    public enum Prioridad {
    	BAJA, MEDIA, ALTA
    }
    
    // Método toString
    @Override
    public String toString() {
        return "OfertaEmpleo{" +
                "ofertaID=" + id +
                ", sectorProyecto='" + sectorProyecto + '\'' +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", modalidadProvincia=" + (modalidadProvincia != null ? modalidadProvincia.getId() : null) +
                ", tipoPerfil=" + (tipoPerfil != null ? tipoPerfil.getId() : null) +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", descripcion='" + descripcion + '\'' +
                ", salario=" + salario +
                ", fechaCreacion=" + fechaCreacion +
                '}';
}
}

