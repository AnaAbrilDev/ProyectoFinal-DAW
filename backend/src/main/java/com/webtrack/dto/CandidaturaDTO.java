package com.webtrack.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.webtrack.model.Candidatura;

/**
 * DTO (Data Transfer Object) para transferir los datos de una candidatura.
 * Incluye información sobre el candidato, el proceso de selección, la fecha de creación
 * y el historial de estados asociados a la candidatura.
 */

public class CandidaturaDTO {
	private Long id;
    private CandidatoDTO candidato;
    private ProcesoSeleccionDTO procesoSeleccion;
    private LocalDateTime fechaCreacion;
    private List<HistorialEstadoDTO> historialEstados; // Lista para historial de estados
    
    public CandidaturaDTO(Candidatura candidatura, List<HistorialEstadoDTO> historialEstados) {
        this.id = candidatura.getId();
        this.fechaCreacion = candidatura.getFechaCreacion();
        
        if(candidatura.getCandidato() != null) {
        	this.candidato = new CandidatoDTO(candidatura.getCandidato());
        }
        
        if(candidatura.getProcesoSeleccion() != null) {
        	this.procesoSeleccion = new ProcesoSeleccionDTO(candidatura.getProcesoSeleccion());
        }
        
        this.historialEstados = historialEstados; // Asignar historial de estados
    
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CandidatoDTO getCandidato() {
		return candidato;
	}
	public void setCandidato(CandidatoDTO candidato) {
		this.candidato = candidato;
	}
	public ProcesoSeleccionDTO getProcesoSeleccion() {
		return procesoSeleccion;
	}
	public void setProcesoSeleccion(ProcesoSeleccionDTO procesoSeleccion) {
		this.procesoSeleccion = procesoSeleccion;
	}
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public List<HistorialEstadoDTO> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<HistorialEstadoDTO> historialEstados) {
        this.historialEstados = historialEstados;
    }
    
    
}
