package com.webtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.model.Provincia;
import com.webtrack.repository.ProvinciaRepositorio;

/**
 * Controlador REST para gestionar la obtención de provincias.
 * Este endpoint proporciona la lista completa de provincias disponibles en la base de datos.
 * Está diseñado únicamente para consulta de datos (solo lectura).
 */

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    private final ProvinciaRepositorio provinciaRepositorio;

    @Autowired
    public ProvinciaController(ProvinciaRepositorio provinciaRepositorio) {
        this.provinciaRepositorio = provinciaRepositorio;
    }

    // Obtener todas las provincias
    @GetMapping
    public ResponseEntity<List<Provincia>> getAllProvincias() {
        List<Provincia> provincias = provinciaRepositorio.findAll();
        return ResponseEntity.ok(provincias);
    }
}
