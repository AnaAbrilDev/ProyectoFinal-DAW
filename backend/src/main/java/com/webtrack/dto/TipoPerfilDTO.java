package com.webtrack.dto;

import com.webtrack.model.TipoPerfil;

/**
 * DTO (Data Transfer Object) para transferir datos simplificados de un tipo de perfil profesional.
 * Incluye solo el id y el nombre, facilitando la respuesta de la API.
 */

public class TipoPerfilDTO {
	private Long id;
    private String nombre;

    public TipoPerfilDTO(TipoPerfil tipoPerfil) {
        this.id = tipoPerfil.getId();
        this.nombre = tipoPerfil.getNombre();
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
