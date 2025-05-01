package com.webtrack.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.TipoPerfilDTO;
import com.webtrack.repository.TipoPerfilRepositorio;

/**
 * Controlador REST para gestionar la obtención de perfiles profesionales.
 * Devuelve la lista de perfiles en un formato simplificado usando DTOs.
 */

@RestController
@RequestMapping("/api/perfiles")
public class TipoPerfilController {

    @Autowired
    private TipoPerfilRepositorio perfilProfesionalRepositorio;

    // Endpoint para obtener todos los perfiles en formato legible
    @GetMapping
    public List<TipoPerfilDTO> getAllPerfiles() {
        return perfilProfesionalRepositorio.findAll().stream()
                .map(perfil -> new TipoPerfilDTO(perfil))
                .collect(Collectors.toList());
    }
}

