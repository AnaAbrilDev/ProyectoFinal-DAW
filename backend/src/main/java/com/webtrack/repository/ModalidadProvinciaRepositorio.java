package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.ModalidadProvincia;

/**
 * Repositorio JPA para la entidad ModalidadProvincia.
 * Permite gestionar la persistencia de la entidad ModalidadProvincia, incluyendo operaciones
 * CRUD y la posibilidad de crear consultas derivadas de métodos.
 */

@Repository
public interface ModalidadProvinciaRepositorio extends JpaRepository<ModalidadProvincia, Long> {
}

