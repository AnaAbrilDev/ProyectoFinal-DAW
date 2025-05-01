package com.webtrack.model;

import java.time.LocalDate;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

/**
 * Entidad que representa a un Candidato en el sistema.
 * Incluye información personal, estado, nivel de inglés, modalidad de trabajo, entre otros.
 * Se gestiona mediante JPA para mapear sus atributos en la tabla "candidatos".
 */

@Entity
@Table(name = "candidatos") 
public class Candidato {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String nombre;
    
    @NotEmpty
    @Column(nullable = false, name = "apellidos")
    private String apellidos;
    
    @NotEmpty
    @Email
    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = true)
    private String telefono;

    @Column(nullable = false, unique = true, name = "nif_nie")
    private String nifNie;

    @Column(nullable = true)
    private Integer salario;

    @Column(nullable = true)
    private String cv;

    @Column(nullable = true, name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    // Enumeración para Estado del Candidato
    public enum EstadoCandidato {
        DISPONIBLE("Disponible"),
        EN_PROCESO("En proceso"),
        CONTRATADO("Contratado"),
        NO_DISPONIBLE("No disponible");

        private final String displayName;

        EstadoCandidato(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCandidato estado;

    // Enumeración para Género
    public enum Genero {
        MASCULINO("Masculino"),
        FEMENINO("Femenino"),
        OTRO("Otro");

        private final String displayName;

        Genero(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Genero genero;

    // Enumeración para Nivel de Inglés
    public enum NivelIngles {
        BAJO("Bajo"),
        MEDIO("Medio"),
        ALTO("Alto");

        private final String displayName;

        NivelIngles(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, name = "nivel_ingles")
    private NivelIngles nivelIngles;

    // Enumeración para Fuente de Reclutamiento
    public enum FuenteReclutamiento {
        INFOJOBS("Infojobs"),
        LINKEDIN("LinkedIn"),
        RECOMENDADO("Recomendado"),
        OTROS("Otros");

        private final String displayName;

        FuenteReclutamiento(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, name = "fuente_reclutamiento")
    private FuenteReclutamiento fuenteReclutamiento;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "modalidad_provincia_id", nullable = true)
    private ModalidadProvincia modalidadProvincia;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "tipo_perfil_id", nullable = true)
    private TipoPerfil tipoPerfil;

    @ManyToMany
    @JoinTable(
        name = "candidatos_tecnologias", // Cambiado a plural
        joinColumns = @JoinColumn(name = "candidato_id"),
        inverseJoinColumns = @JoinColumn(name = "tecnologia_id")
    )
    private Set<Tecnologia> tecnologias = new HashSet<>();
    
    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Candidatura> candidaturas = new HashSet<>();


    // Constructor vacío
    public Candidato() {}

    // Getters y Setters
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNifNie() {
        return nifNie;
    }

    public void setNifNie(String nifNie) {
        this.nifNie = nifNie;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstadoCandidato getEstado() {
        return estado;
    }

    public void setEstado(EstadoCandidato estado) {
        this.estado = estado;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public NivelIngles getNivelIngles() {
        return nivelIngles;
    }

    public void setNivelIngles(NivelIngles nivelIngles) {
        this.nivelIngles = nivelIngles;
    }

    public FuenteReclutamiento getFuenteReclutamiento() {
        return fuenteReclutamiento;
    }

    public void setFuenteReclutamiento(FuenteReclutamiento fuenteReclutamiento) {
        this.fuenteReclutamiento = fuenteReclutamiento;
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

    public Set<Tecnologia> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(Set<Tecnologia> tecnologias) {
        this.tecnologias = tecnologias;
    }
}



