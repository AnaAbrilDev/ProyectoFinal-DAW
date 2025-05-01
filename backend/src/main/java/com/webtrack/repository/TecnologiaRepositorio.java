package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.Tecnologia;

/**
 * Repositorio JPA para la entidad Tecnologia.
 * Proporciona métodos CRUD básicos para gestionar las tecnologías en la base de datos.
 */

@Repository
public interface TecnologiaRepositorio extends JpaRepository<Tecnologia, Long> {}

