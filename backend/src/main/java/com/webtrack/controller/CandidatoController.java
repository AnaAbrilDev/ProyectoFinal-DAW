package com.webtrack.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.CandidatoDTO;
import com.webtrack.model.Candidato;
import com.webtrack.service.CandidatoService;

/**
 * Controlador que gestiona las operaciones CRUD para la entidad Candidato.
 * Proporciona endpoints para crear, leer, actualizar y eliminar candidatos.
 */

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

	private final CandidatoService candidatoService;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @GetMapping
    public ResponseEntity<List<CandidatoDTO>> obtenerTodos() {
        List<CandidatoDTO> candidatos = candidatoService.obtenerTodos();
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatoDTO> obtenerPorId(@PathVariable Long id) {
        CandidatoDTO candidato = candidatoService.obtenerPorId(id);
        return ResponseEntity.ok(candidato);
    }
    
    @PostMapping
    public ResponseEntity<?> crearCandidato(@RequestBody Candidato candidato) {
        try {
            CandidatoDTO nuevoCandidato = candidatoService.crearCandidato(candidato);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCandidato);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCandidato(@PathVariable Long id, @RequestBody Candidato candidato) {
        try {
            CandidatoDTO candidatoActualizado = candidatoService.actualizarCandidato(id, candidato);
            return ResponseEntity.ok(candidatoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Eliminar un candidato
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCandidato(@PathVariable Long id) {
        try {
            candidatoService.eliminarCandidato(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}






