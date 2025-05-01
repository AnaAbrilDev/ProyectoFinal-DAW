package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.TipoPerfil;

/**
 * Repositorio JPA para la entidad TipoPerfil.
 * Proporciona métodos CRUD básicos para gestionar los tipos de perfil en la base de datos.
 */

@Repository
public interface TipoPerfilRepositorio extends JpaRepository<TipoPerfil, Long> {

}

