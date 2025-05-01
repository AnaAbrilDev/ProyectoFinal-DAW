package com.webtrack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.Usuario;

/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona métodos CRUD básicos para gestionar los usuarios en la base de datos.
 * Incluye consultas personalizadas para encontrar usuarios por su correo electrónico,
 * nombre o username.
 */

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByUsername(String username);
}
