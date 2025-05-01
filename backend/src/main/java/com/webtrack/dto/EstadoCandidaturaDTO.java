package com.webtrack.dto;

import com.webtrack.model.EstadoCandidatura;

/**
 * DTO (Data Transfer Object) para transferir los datos de un estado de candidatura.
 * Incluye información sobre el identificador único (id) y el nombre del estado.
 */

public class EstadoCandidaturaDTO {
	private Long id;
    private String nombre;

    public EstadoCandidaturaDTO(EstadoCandidatura estado) {
        this.id = estado.getId();
        this.nombre = estado.getNombre();
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
    
    
}
