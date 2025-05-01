import { useState } from "react";

/**
 * Hook personalizado para manejar la paginación de datos
 * @param {Array} data - El conjunto de datos que se quiere paginar 
 * @param {number} itemsPerPage - Número de elementos por página, establecido por defecto en 5 
 * @returns {Object} - Un objeto con los datos paginados, la página actual, el total de páginas y funciones para cambiar de página
 */

export default function useTable(data, itemsPerPage = 5) {
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = Math.ceil(data.length / itemsPerPage);

  const paginatedData = data.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

  const nextPage = () => setCurrentPage((prev) => (prev < totalPages ? prev + 1 : prev));
  const prevPage = () => setCurrentPage((prev) => (prev > 1 ? prev - 1 : prev));

  return { paginatedData, currentPage, totalPages, nextPage, prevPage };
}