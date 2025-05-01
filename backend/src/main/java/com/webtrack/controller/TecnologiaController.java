package com.webtrack.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.TecnologiaDTO;
import com.webtrack.repository.TecnologiaRepositorio;

/**
 * Controlador REST para gestionar la obtención de tecnologías.
 * Devuelve la lista de tecnologías en un formato simplificado usando DTOs.
 */

@RestController
@RequestMapping("/api/tecnologias")
public class TecnologiaController {

    @Autowired
    private TecnologiaRepositorio tecnologiaRepositorio;

    // Endpoint para obtener todas las tecnologías en formato legible
    @GetMapping
    public List<TecnologiaDTO> getAllTecnologias() {
        return tecnologiaRepositorio.findAll().stream()
                .map(tecnologia -> new TecnologiaDTO(tecnologia)) 
                .collect(Collectors.toList());
    }
}

