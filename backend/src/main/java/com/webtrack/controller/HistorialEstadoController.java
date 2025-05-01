package com.webtrack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.HistorialEstadoDTO;
import com.webtrack.service.HistorialEstadoService;

/**
 * Controlador REST para gestionar el historial de estados de las candidaturas.
 * Permite agregar nuevos estados al historial de una candidatura específica.
 */

@RestController
@RequestMapping("/api/historial-estados")
public class HistorialEstadoController {

    private final HistorialEstadoService historialEstadoService;

    public HistorialEstadoController(HistorialEstadoService historialEstadoService) {
        this.historialEstadoService = historialEstadoService;
    }

    @PostMapping("/candidatura/{candidatura_id}/estado/{estado_id}")
    public ResponseEntity<?> agregarEstado(@PathVariable Long candidatura_id,
                                           @PathVariable Long estado_id,
                                           @RequestParam(value = "usuario_id", required = false) Long usuarioId) {
        try {
        
            HistorialEstadoDTO historial = historialEstadoService.agregarEstado(candidatura_id, estado_id, usuarioId);
            return ResponseEntity.ok(historial);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}