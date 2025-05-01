package com.webtrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtrack.model.Rol;

/**
 * Repositorio JPA para la entidad Rol.
 * Proporciona métodos CRUD básicos para gestionar los roles en la base de datos.
 * Incluye una consulta derivada para buscar un rol por su nombre.
 */

public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);

}
