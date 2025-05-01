package com.webtrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webtrack.model.ProcesoSeleccion;

/**
 * Repositorio JPA para la entidad ProcesoSeleccion.
 * Proporciona métodos CRUD básicos para gestionar procesos de selección y 
 * admite la creación de consultas personalizadas a través de Spring Data JPA.
 */

@Repository
public interface ProcesoSeleccionRepositorio extends JpaRepository<ProcesoSeleccion, Long> {

	// Consulta personalizada para obtener procesos de selección con relaciones asociadas
    /*@Query("SELECT o FROM OfertaEmpleo o")
    @EntityGraph(attributePaths = {"ubicacion", "usuario", "perfilProfesional"})
    List<ProcesoSeleccion> findAllWithRelations();*/
}

