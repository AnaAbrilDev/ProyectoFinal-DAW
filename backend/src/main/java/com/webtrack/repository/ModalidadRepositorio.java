package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtrack.model.Modalidad;

/**
 * Repositorio JPA para la entidad Modalidad.
 * Permite realizar operaciones CRUD (Create, Read, Update, Delete)
 * y consultas personalizadas sobre la entidad Modalidad.
 */

public interface ModalidadRepositorio extends JpaRepository<Modalidad, Long> {
}
