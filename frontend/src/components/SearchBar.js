import React from "react";
/**
 * Componente funcional SearchBar
 * Este componente muestra una barra de búsqueda simple con funcionalidad controlada
 * 
 * Props:
 * @param {string} search - Valor actual del campo de búsqueda (controlado externamente)
 * @param {function} setSearch - Función para actualizar el valor del campo de búsqueda
 * @param {string} [placeholder="Buscar..."] - Texto opcional que se muestra en el campo de entrada 
 */

export default function SearchBar({ search, setSearch, placeholder }) {
  return (
    <input
      type="text"
      className="form-control mb-3"
      placeholder={placeholder || "Buscar..."}
      value={search}
      onChange={(e) => setSearch(e.target.value)}
    />
  );
}
