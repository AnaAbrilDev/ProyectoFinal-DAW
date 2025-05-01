package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.Candidato;

/**
 * Repositorio JPA para la entidad Candidato.
 * Esta interfaz permite realizar operaciones CRUD (Create, Read, Update, Delete)
 * y consultas personalizadas sobre la entidad Candidato. 
 */

@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {}
