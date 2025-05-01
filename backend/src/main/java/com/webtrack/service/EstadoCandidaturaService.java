package com.webtrack.service;

import com.webtrack.dto.EstadoCandidaturaDTO;
import com.webtrack.repository.EstadoCandidaturaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar los estados de las candidaturas.
 * Proporciona métodos para obtener y manipular datos relacionados con los estados de las candidaturas.
 */

@Service
public class EstadoCandidaturaService {

    private final EstadoCandidaturaRepositorio estadoCandidaturaRepositorio;

    public EstadoCandidaturaService(EstadoCandidaturaRepositorio estadoCandidaturaRepositorio) {
        this.estadoCandidaturaRepositorio = estadoCandidaturaRepositorio;
    }

    // Obtener todos los estados de candidatura como DTOs
    public List<EstadoCandidaturaDTO> obtenerTodosLosEstados() {
        return estadoCandidaturaRepositorio.findAll()
                .stream()
                .map(estado -> new EstadoCandidaturaDTO(estado))
                .collect(Collectors.toList());
    }
}