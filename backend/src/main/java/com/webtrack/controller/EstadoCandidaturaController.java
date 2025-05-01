package com.webtrack.controller;

import com.webtrack.dto.EstadoCandidaturaDTO;
import com.webtrack.service.EstadoCandidaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con
 * los estados de la candidatura. Permite obtener información sobre
 * los diferentes estados que puede tener una candidatura.
 */

@RestController
@RequestMapping("/api/estados_candidatura")
public class EstadoCandidaturaController {

    private final EstadoCandidaturaService estadoCandidaturaService;

    public EstadoCandidaturaController(EstadoCandidaturaService estadoCandidaturaService) {
        this.estadoCandidaturaService = estadoCandidaturaService;
    }

    // Endpoint para obtener todos los estados de candidatura
    @GetMapping
    public ResponseEntity<List<EstadoCandidaturaDTO>> obtenerTodosLosEstados() {
        List<EstadoCandidaturaDTO> estados = estadoCandidaturaService.obtenerTodosLosEstados();
        return ResponseEntity.ok(estados);
    }
}
