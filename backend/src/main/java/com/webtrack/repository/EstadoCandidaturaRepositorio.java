package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.EstadoCandidatura;

/**
 * Repositorio JPA para la entidad EstadoCandidatura. 
 * Proporciona operaciones CRUD estándar y permite la creación de consultas personalizadas.
 */

@Repository
public interface EstadoCandidaturaRepositorio extends JpaRepository<EstadoCandidatura, Long> {
}
