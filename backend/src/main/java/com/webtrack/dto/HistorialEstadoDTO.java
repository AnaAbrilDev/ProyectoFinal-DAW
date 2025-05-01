package com.webtrack.dto;

import java.time.LocalDateTime;

import com.webtrack.model.HistorialEstadoCandidatura;

/**
 * DTO (Data Transfer Object) para transferir los datos de los cambios de estado
 * de una candidatura. Incluye información sobre el estado, el usuario responsable y la fecha del cambio
 */

public class HistorialEstadoDTO {
	
	private String estado; // Nombre del estado en el momento del cambio
    private UsuarioDTO usuario; // Usuario responsable del cambio
    private LocalDateTime fechaCambio; // Fecha y hora del cambio de estado
    
    /**
     * Constructor que convierte una entidad HistorialEstadoCandidatura en un DTO.
     * 
     * @param historial La entidad de la base de datos que contiene los detalles del cambio de estado
     */
    public HistorialEstadoDTO(HistorialEstadoCandidatura historial) {
        this.estado = historial.getEstado().getNombre();
        if(historial.getUsuario() != null) {
        	this.usuario = new UsuarioDTO(historial.getUsuario());
        }
        this.fechaCambio = historial.getFechaCambio();
    }

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDateTime fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
    
    
}
