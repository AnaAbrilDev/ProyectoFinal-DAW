package com.webtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.ModalidadProvinciaDTO;
import com.webtrack.service.ProcesoSeleccionService;

/**
 * Controlador REST para gestionar la relación entre modalidades de trabajo y provincias.
 * Este endpoint permite obtener información detallada sobre las modalidades disponibles
 * en cada provincia, facilitando la gestión de procesos de selección.
 */
@RestController
@RequestMapping("/api/modalidades_provincia")
public class ModalidadProvinciaController {
	
	// Servicio que gestiona la lógica relacionada con los procesos de selección
    private final ProcesoSeleccionService procesoSeleccionService;

    @Autowired
    public ModalidadProvinciaController(ProcesoSeleccionService procesoSeleccionService) {
        this.procesoSeleccionService = procesoSeleccionService;
    }

    @GetMapping
    public ResponseEntity<List<ModalidadProvinciaDTO>> getModalidadesProvincia() {
        List<ModalidadProvinciaDTO> modalidadesProvincia = procesoSeleccionService.obtenerModalidadesProvincia();
        return ResponseEntity.ok(modalidadesProvincia);
    }
}




