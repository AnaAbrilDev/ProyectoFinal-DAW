package com.webtrack.dto;

import com.webtrack.model.Tecnologia;

/**
 * DTO (Data Transfer Object) para transferir datos simplificados de una tecnología.
 * Incluye solo el id y el nombre, facilitando la respuesta de la API.
 */

public class TecnologiaDTO {
	private Long id;
    private String nombre;

    public TecnologiaDTO(Tecnologia tecnologia) {
        this.id = tecnologia.getId();
        this.nombre = tecnologia.getNombre();
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
