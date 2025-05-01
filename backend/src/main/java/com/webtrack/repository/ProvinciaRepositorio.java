package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtrack.model.Provincia;

/**
 * Repositorio JPA para la entidad Provincia.
 * Proporciona métodos CRUD básicos para gestionar las provincias en la base de datos.
 */

public interface ProvinciaRepositorio extends JpaRepository<Provincia, Long> {
}
