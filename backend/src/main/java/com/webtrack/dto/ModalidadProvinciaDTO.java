package com.webtrack.dto;

import com.webtrack.model.ModalidadProvincia;

/**
 * DTO para transferir datos relacionados con la combinación de una provincia
 * y su modalidad de trabajo (presencial, híbrido, remoto).
 *
 */

public class ModalidadProvinciaDTO {
    private Long id; // Identificado único de la combinación provincia-modalidad
    private String provincia;
    private String modalidad;

    public ModalidadProvinciaDTO(ModalidadProvincia modalidadProvincia) {
        this.id = modalidadProvincia.getId();
        this.provincia = modalidadProvincia.getProvincia().getNombre();
        this.modalidad = modalidadProvincia.getModalidad().getNombre();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

    
}