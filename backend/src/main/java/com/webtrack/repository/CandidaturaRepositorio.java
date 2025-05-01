package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.Candidatura;

/**
 * Repositorio JPA para la entidad Candidatura
 * Proporciona operaciones CRUD estándar y permite la creación de consultas personalizadas.
 */
@Repository
public interface CandidaturaRepositorio extends JpaRepository<Candidatura, Long> {

}
