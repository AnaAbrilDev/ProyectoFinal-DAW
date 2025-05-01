package com.webtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.model.Modalidad;
import com.webtrack.repository.ModalidadRepositorio;

/**
 * Controlador REST para la gestión de las modalidades de trabajo
 * Proporciona un punto de acceso para obtener todas las modalidades disponibles en la base de datos.
 * Este controlador está diseñado únicamente para la consulta de datos (solo lectura).
 */

@RestController
@RequestMapping("/api/modalidades")
public class ModalidadController {

    private final ModalidadRepositorio modalidadRepositorio;

    @Autowired
    public ModalidadController(ModalidadRepositorio modalidadRepositorio) {
        this.modalidadRepositorio = modalidadRepositorio;
    }

    // Obtener todas las modalidades
    @GetMapping
    public ResponseEntity<List<Modalidad>> getAllModalidades() {
        List<Modalidad> modalidades = modalidadRepositorio.findAll();
        return ResponseEntity.ok(modalidades);
    }
}
