package com.webtrack.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import com.webtrack.model.Candidato;

/**
 * DTO (Data Transfer Object) para representar la información de un candidato.
 * Este DTO se utiliza para transferir datos de forma limpia y simplificada
 * en las respuestas de la API.
 */


public class CandidatoDTO {
	private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String nifNie;
    private Integer salario;
    private String cv;
    private LocalDate fechaNacimiento;
    private String estado; // Estado del candidato (enum como String)
    private String genero; // Género del candidato (enum como String)
    private String nivelIngles; // Nivel de inglés (enum como String)
    private String fuenteReclutamiento; // Fuente de reclutamiento (enum como String)
    private ModalidadProvinciaDTO modalidadProvincia; // Detalles de la modalidad y provincia
    private TipoPerfilDTO tipoPerfil; // Detalles del tipo de perfil
    private Set<TecnologiaDTO> tecnologias; // Lista de nombres de tecnologías asociadas

    // Constructor
    public CandidatoDTO(Candidato candidato) {
        this.id = candidato.getId();
        this.nombre = candidato.getNombre();
        this.apellidos = candidato.getApellidos();
        this.correo = candidato.getCorreo();
        this.telefono = candidato.getTelefono();
        this.nifNie = candidato.getNifNie();
        this.salario = candidato.getSalario();
        this.cv = candidato.getCv();
        this.fechaNacimiento = candidato.getFechaNacimiento();
        this.estado = candidato.getEstado() != null ? candidato.getEstado().getDisplayName() : null;
        this.genero = candidato.getGenero() != null ? candidato.getGenero().getDisplayName() : null;
        this.nivelIngles = candidato.getNivelIngles() != null ? candidato.getNivelIngles().getDisplayName() : null;
        this.fuenteReclutamiento = candidato.getFuenteReclutamiento() != null ? candidato.getFuenteReclutamiento().getDisplayName() : null;

        if (candidato.getModalidadProvincia() != null) {
            this.modalidadProvincia = new ModalidadProvinciaDTO(candidato.getModalidadProvincia());
        }

        if (candidato.getTipoPerfil() != null) {
            this.tipoPerfil = new TipoPerfilDTO(candidato.getTipoPerfil());
        }

        if (candidato.getTecnologias() != null) {
            this.tecnologias = candidato.getTecnologias().stream()
                    .map(tecnologia -> new TecnologiaDTO(tecnologia))
                    .collect(Collectors.toSet());
        }
    }

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

	public ModalidadProvinciaDTO getModalidadProvincia() {
		return modalidadProvincia;
	}

	public void setModalidadProvincia(ModalidadProvinciaDTO modalidadProvincia) {
		this.modalidadProvincia = modalidadProvincia;
	}

	public TipoPerfilDTO getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(TipoPerfilDTO tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNivelIngles() {
		return nivelIngles;
	}

	public void setNivelIngles(String nivelIngles) {
		this.nivelIngles = nivelIngles;
	}

	public String getFuenteReclutamiento() {
		return fuenteReclutamiento;
	}

	public void setFuenteReclutamiento(String fuenteReclutamiento) {
		this.fuenteReclutamiento = fuenteReclutamiento;
	}

	public Set<TecnologiaDTO> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(Set<TecnologiaDTO> tecnologias) {
		this.tecnologias = tecnologias;
	}

	
    
    
}
