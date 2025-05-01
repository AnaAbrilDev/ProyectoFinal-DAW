package com.webtrack.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.webtrack.model.ProcesoSeleccion;
import com.webtrack.model.ProcesoSeleccion.EstadoProceso;
import com.webtrack.model.ProcesoSeleccion.Prioridad;

/**
 * DTO (Data Transfer Object) para transferir los datos de un proceso de selección.
 * Incluye información sobre el sector del proyecto, estado, prioridad, modalidad, perfil profesional,
 * usuario asociado, descripción, salario, fecha de creación y tecnologías relacionadas.
 */

public class ProcesoSeleccionDTO {
    private Long id;
    private String sectorProyecto;
    private EstadoProceso estado;
    private Prioridad prioridad;
    private ModalidadProvinciaDTO modalidadProvincia; // Detalles de la modalidad y provincia
    private TipoPerfilDTO tipoPerfil; // Detalles del tipo de perfil
    private UsuarioDTO usuario; // ID del usuario asociado
    private String descripcion;
    private Double salario;
    private LocalDateTime fechaCreacion;
    private Set<TecnologiaDTO> tecnologias; // Nombres de las tecnologías asociadas

    public ProcesoSeleccionDTO(ProcesoSeleccion procesoSeleccion) {
        this.id = procesoSeleccion.getId();
        this.sectorProyecto = procesoSeleccion.getSectorProyecto();
        this.estado = procesoSeleccion.getEstado();
        this.prioridad = procesoSeleccion.getPrioridad();
        this.descripcion = procesoSeleccion.getDescripcion();
        this.salario = procesoSeleccion.getSalario();
        this.fechaCreacion = procesoSeleccion.getFechaCreacion();
        
        if(procesoSeleccion.getModalidadProvincia() != null) {
        	this.modalidadProvincia = new ModalidadProvinciaDTO(procesoSeleccion.getModalidadProvincia());
        }
        
        if(procesoSeleccion.getTipoPerfil() != null) {
        	this.tipoPerfil = new TipoPerfilDTO(procesoSeleccion.getTipoPerfil());
        }
        
        if(procesoSeleccion.getUsuario() != null) {
        	this.usuario = new UsuarioDTO(procesoSeleccion.getUsuario());
        }
        
        if (procesoSeleccion.getTecnologias() != null) {
            this.tecnologias = procesoSeleccion.getTecnologias().stream()
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Set<TecnologiaDTO> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(Set<TecnologiaDTO> tecnologias) {
		this.tecnologias = tecnologias;
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

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
    
    

}