package com.webtrack.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.webtrack.dto.CandidaturaDTO;
import com.webtrack.dto.HistorialEstadoDTO;
import com.webtrack.model.Candidato;
import com.webtrack.model.Candidatura;
import com.webtrack.model.EstadoCandidatura;
import com.webtrack.model.HistorialEstadoCandidatura;
import com.webtrack.model.ProcesoSeleccion;
import com.webtrack.model.Usuario;
import com.webtrack.repository.CandidatoRepositorio;
import com.webtrack.repository.CandidaturaRepositorio;
import com.webtrack.repository.HistorialEstadoCandidaturaRepositorio;
import com.webtrack.repository.ProcesoSeleccionRepositorio;

/**
 * Servicio para gestionar candidaturas en procesos de selección.
 * Incluye seguimiento de historial de estados y asociación con candidatos/procesos.
 */

@Service
public class CandidaturaService {

    private final CandidaturaRepositorio candidaturaRepositorio;
    private final CandidatoRepositorio candidatoRepositorio;
    private final ProcesoSeleccionRepositorio procesoSeleccionRepositorio;
    private final HistorialEstadoCandidaturaRepositorio historialEstadoCandidaturaRepositorio;

    public CandidaturaService(CandidaturaRepositorio candidaturaRepositorio,
                              CandidatoRepositorio candidatoRepositorio,
                              ProcesoSeleccionRepositorio procesoSeleccionRepositorio,
                              HistorialEstadoCandidaturaRepositorio historialEstadoCandidaturaRepositorio) {
        this.candidaturaRepositorio = candidaturaRepositorio;
        this.candidatoRepositorio = candidatoRepositorio;
        this.procesoSeleccionRepositorio = procesoSeleccionRepositorio;
        this.historialEstadoCandidaturaRepositorio = historialEstadoCandidaturaRepositorio;
    }

    public List<CandidaturaDTO> obtenerTodas() {
        return candidaturaRepositorio.findAll().stream()
                .map(candidatura -> {
                    // Obtener el historial de estados para cada candidatura
                    List<HistorialEstadoDTO> historialEstados = historialEstadoCandidaturaRepositorio
                            .findByCandidatura(candidatura)
                            .stream()
                            .map(HistorialEstadoDTO::new) // Convertir a DTO
                            .collect(Collectors.toList());
                    
                    // Retornar el DTO de la candidatura con su historial
                    return new CandidaturaDTO(candidatura, historialEstados);
                })
                .collect(Collectors.toList());
    }


    public CandidaturaDTO obtenerPorId(Long id) {
        return candidaturaRepositorio.findById(id)
                .map(candidatura -> {
                    // Obtener el historial de estados para la candidatura
                    List<HistorialEstadoDTO> historialEstados = historialEstadoCandidaturaRepositorio
                            .findByCandidatura(candidatura)
                            .stream()
                            .map(HistorialEstadoDTO::new) // Convertir a DTO
                            .collect(Collectors.toList());
                    
                    // Retornar el DTO de la candidatura con su historial
                    return new CandidaturaDTO(candidatura, historialEstados);
                })
                .orElseThrow(() -> new RuntimeException("Candidatura no encontrada con ID: " + id));
    }


    public CandidaturaDTO crearCandidatura(Candidatura candidaturaRequest) {
        Candidato candidato = candidatoRepositorio.findById(candidaturaRequest.getCandidato().getId())
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con ID: " + candidaturaRequest.getCandidato().getId()));

        ProcesoSeleccion procesoSeleccion = procesoSeleccionRepositorio.findById(candidaturaRequest.getProcesoSeleccion().getId())
                .orElseThrow(() -> new RuntimeException("Proceso de selección no encontrado con ID: " + candidaturaRequest.getProcesoSeleccion().getId()));

        // Crear una nueva instancia de candidatura y setear las propiedades
        Candidatura candidatura = new Candidatura();
        candidatura.setCandidato(candidato);
        candidatura.setProcesoSeleccion(procesoSeleccion);
        candidatura.setFechaCreacion(java.time.LocalDateTime.now());
        candidatura.setUltimaActualizacion(java.time.LocalDateTime.now());

        // Guardar candidatura
        Candidatura guardada = candidaturaRepositorio.save(candidatura);

        // Devolver el DTO
        return obtenerPorId(guardada.getId());
    }

    public void eliminarCandidatura(Long id) {
        if (!candidaturaRepositorio.existsById(id)) {
            throw new RuntimeException("Candidatura no encontrada con ID: " + id);
        }
        candidaturaRepositorio.deleteById(id);
    }
    
    public void asignarEstadoInicial(Long candidaturaId, EstadoCandidatura estado, Usuario usuario) {
        Candidatura candidatura = candidaturaRepositorio.findById(candidaturaId)
                .orElseThrow(() -> new RuntimeException("Candidatura no encontrada con ID: " + candidaturaId));

        // Crear un historial de estado con usuario opcional
        HistorialEstadoCandidatura historial = new HistorialEstadoCandidatura();
        historial.setCandidatura(candidatura);
        historial.setEstado(estado);
        historial.setUsuario(usuario); // Esto será null si no se proporciona usuario
        historial.setFechaCambio(LocalDateTime.now());

        historialEstadoCandidaturaRepositorio.save(historial);
    }


}