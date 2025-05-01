package com.webtrack.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webtrack.dto.HistorialEstadoDTO;
import com.webtrack.model.Candidatura;
import com.webtrack.model.EstadoCandidatura;
import com.webtrack.model.HistorialEstadoCandidatura;
import com.webtrack.model.Usuario;
import com.webtrack.repository.CandidaturaRepositorio;
import com.webtrack.repository.EstadoCandidaturaRepositorio;
import com.webtrack.repository.HistorialEstadoCandidaturaRepositorio;
import com.webtrack.repository.UsuarioRepositorio;

/**
 * Servicio para gestionar el historial de cambios de estado de una candidatura.
 * Permite agregar un nuevo estado al historial y asociarlo opcionalmente a un usuario.
 */

@Service
public class HistorialEstadoService {
	
	// Repositorios para interactuar con las entidades relacionadas
    private final HistorialEstadoCandidaturaRepositorio historialRepositorio;
    private final CandidaturaRepositorio candidaturaRepositorio;
    private final EstadoCandidaturaRepositorio estadoCandidaturaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    // Constructor que inyecta las dependencias
    public HistorialEstadoService(HistorialEstadoCandidaturaRepositorio historialRepositorio,
                                  CandidaturaRepositorio candidaturaRepositorio,
                                  EstadoCandidaturaRepositorio estadoCandidaturaRepositorio,
                                  UsuarioRepositorio usuarioRepositorio) {
        this.historialRepositorio = historialRepositorio;
        this.candidaturaRepositorio = candidaturaRepositorio;
        this.estadoCandidaturaRepositorio = estadoCandidaturaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Transactional
    public HistorialEstadoDTO agregarEstado(Long candidaturaId, Long estadoId, Long usuarioId) {
        // Buscar la candidatura
        Candidatura candidatura = candidaturaRepositorio.findById(candidaturaId)
                .orElseThrow(() -> new RuntimeException("Candidatura no encontrada con ID: " + candidaturaId));

        // Buscar el estado
        EstadoCandidatura estado = estadoCandidaturaRepositorio.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + estadoId));

        // Buscar el usuario si se proporciona
        Usuario usuario = null;
        if (usuarioId != null) {
            System.out.println("Buscando usuario con ID: " + usuarioId);
            usuario = usuarioRepositorio.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
            System.out.println("Usuario encontrado: " + usuario.getId() + ", Email: " + usuario.getEmail());
        } else {
            System.out.println("UsuarioID es null, no se asignará un usuario.");
        }

        // Crear el historial
        HistorialEstadoCandidatura historial = new HistorialEstadoCandidatura(candidatura, estado, usuario);
        historial.setFechaCambio(LocalDateTime.now());
        historialRepositorio.save(historial);

        // Retornar el DTO
        return new HistorialEstadoDTO(historial);
    }
}
