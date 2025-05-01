import React from "react";
/**
 * Componente de paginación simple.
 * Permite la navegación entre páginas y muestra el estado actual
 * @param {number} currentPage - Página actual
 * @param {number} totalPages - Número total de páginas disponibles
 * @param {function} prevPage - Función para retroceder una página
 * @param {function} nextPage - Función para avanzar una página
 */

export default function Pagination({ currentPage, totalPages, prevPage, nextPage }) {
  return (
    <div className="d-flex justify-content-between mt-3">
      {/* Botón para ir a la página anterior */}
      {/* Deshabilitado si se está en la primera página */}
      <button className="btn btn-secondary" onClick={prevPage} disabled={currentPage === 1}>
        Anterior
      </button>
      <span>Página {currentPage} de {totalPages}</span>
      <button className="btn btn-secondary" onClick={nextPage} disabled={currentPage === totalPages}>
        Siguiente
      </button>
    </div>
  );
}
