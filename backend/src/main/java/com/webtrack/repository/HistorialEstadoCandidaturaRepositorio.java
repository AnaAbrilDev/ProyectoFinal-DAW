package com.webtrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.Candidatura;
import com.webtrack.model.HistorialEstadoCandidatura;

/**
 * Repositorio JPA para la entidad HistorialEstadoCandidatura.
 * Proporciona operaciones CRUD estándar y permite consultar el historial de estados
 * asociados a una candidatura específica.
 */

@Repository
public interface HistorialEstadoCandidaturaRepositorio extends JpaRepository<HistorialEstadoCandidatura, Long> {
    List<HistorialEstadoCandidatura> findByCandidatura(Candidatura candidatura);
}
