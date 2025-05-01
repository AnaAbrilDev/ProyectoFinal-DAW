package com.webtrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.ProcesoSeleccionDTO;
import com.webtrack.model.ProcesoSeleccion;
import com.webtrack.service.ProcesoSeleccionService;

/**
 * Contrrolador REST para gestionar las operaciones CRUD sobre la entidad ProcesoSeleccion.
 * Este controlador permite crear, leer, actualizar y eliminar procesos de selección.
 */

@RestController
@RequestMapping("/api/procesos_seleccion")
public class ProcesoSeleccionController {

    private final ProcesoSeleccionService procesoSeleccionService;

    public ProcesoSeleccionController(ProcesoSeleccionService procesoSeleccionService) {
        this.procesoSeleccionService = procesoSeleccionService;
    }

    // Obtener todos los procesos de selección
    @GetMapping
    public ResponseEntity<List<ProcesoSeleccionDTO>> getAllProcesos() {
        List<ProcesoSeleccionDTO> procesos = procesoSeleccionService.obtenerTodos();
        return ResponseEntity.ok(procesos);
    }

    // Crear un nuevo proceso de selección
    @PostMapping
    public ResponseEntity<ProcesoSeleccionDTO> createProceso(@RequestBody ProcesoSeleccion procesoSeleccion) {
        ProcesoSeleccion nuevoProceso = procesoSeleccionService.crearProceso(procesoSeleccion);
        ProcesoSeleccionDTO procesoDTO = new ProcesoSeleccionDTO(nuevoProceso);
        return ResponseEntity.status(201).body(procesoDTO);
    }

    // Obtener un proceso de selección por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProcesoSeleccionDTO> getProcesoById(@PathVariable Long id) {
        ProcesoSeleccionDTO procesoDTO = procesoSeleccionService.obtenerPorId(id);
        return ResponseEntity.ok(procesoDTO);
    }

    // Actualizar un proceso de selección existente
    @PutMapping("/{id}")
    public ResponseEntity<ProcesoSeleccionDTO> updateProceso(@PathVariable Long id, @RequestBody ProcesoSeleccion updatedProceso) {
        ProcesoSeleccion procesoActualizado = procesoSeleccionService.actualizarProceso(id, updatedProceso);
        ProcesoSeleccionDTO procesoDTO = new ProcesoSeleccionDTO(procesoActualizado);
        return ResponseEntity.ok(procesoDTO);
    }

    // Eliminar un proceso de selección por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProceso(@PathVariable Long id) {
        procesoSeleccionService.eliminarProceso(id);
        return ResponseEntity.noContent().build();
    }
}
