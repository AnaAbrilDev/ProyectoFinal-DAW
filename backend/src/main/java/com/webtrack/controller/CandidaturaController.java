package com.webtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.CandidaturaDTO;
import com.webtrack.model.Candidatura;
import com.webtrack.model.EstadoCandidatura;
import com.webtrack.repository.EstadoCandidaturaRepositorio;
import com.webtrack.service.CandidaturaService;

/**
 * Controlador REST para gestionar las operaciones CRUD relacionadas con las candidaturas
 */

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {
	
	// Inyección del servicio que maneja la lógica de negocio de las candidaturas
    @Autowired
    private CandidaturaService candidaturaService;

    // Repositorio para acceder a los datos de los estados de las candidaturas
    @Autowired
    private EstadoCandidaturaRepositorio estadoCandidaturaRepositorio;

    @Value("${estado.candidatura.inicial-id}") // ID del estado inicial en application.properties
    private Long estadoInicialId;

    // Obtener todas las candidaturas
    @GetMapping
    public List<CandidaturaDTO> getAllCandidaturas() {
        return candidaturaService.obtenerTodas();
    }

    // Crear una nueva candidatura
    @PostMapping
    public ResponseEntity<?> createCandidatura(@RequestBody Candidatura candidatura) {
        try {
            // Crear la candidatura
            CandidaturaDTO nuevaCandidatura = candidaturaService.crearCandidatura(candidatura);

            // Obtener el estado inicial
            EstadoCandidatura estadoInicial = estadoCandidaturaRepositorio.findById(estadoInicialId)
                    .orElseThrow(() -> new RuntimeException("Estado inicial no encontrado con ID: " + estadoInicialId));

         // Asignar el estado inicial en el historial
            candidaturaService.asignarEstadoInicial(nuevaCandidatura.getId(), estadoInicial, null);

            return ResponseEntity.status(201).body(nuevaCandidatura);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Obtener una candidatura por ID
    @GetMapping("/{id}")
    public CandidaturaDTO getCandidaturaById(@PathVariable Long id) {
        return candidaturaService.obtenerPorId(id);
    }

    // Eliminar una candidatura por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCandidatura(@PathVariable Long id) {
        try {
            candidaturaService.eliminarCandidatura(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}