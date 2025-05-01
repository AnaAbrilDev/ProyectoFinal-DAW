import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom"; // Enlaces para la navegación entre rutas
import { obtenerCandidatos, eliminarCandidato } from "../services/candidatoService"; // Funciones del servicio para obtener y eliminar candidatos
import useTable from "../../../hooks/useTable"; // Hook personalizado para la lógica de paginación
import SearchBar from "../../../components/SearchBar"; 
import Pagination from "../../../components/Pagination"; 
import { FaUser, FaEnvelope, FaPhone, FaTrash, FaEdit, FaEye, FaBriefcase } from "react-icons/fa"; 

/**
 * Componente funcional que lista los candidatos disponible en formato de tarjetas
 * Permite buscar, filtrar, ver detalles, editar y eliminar candidatos.
 */

export default function ListadoCandidatos() {
  const [candidatos, setCandidatos] = useState([]);
  const [search, setSearch] = useState("");

  // Hook de efecto para cargar los candidatos cuando se monta el componente
  useEffect(() => {
    cargarCandidatos();
  }, []);

  /**
   * Función asíncrona para obtener los candidatos del backend
   * Maneja errores en caso de que la API no responda correctamente
   */
  const cargarCandidatos = async () => {
    try {
      const result = await obtenerCandidatos();
      setCandidatos(result);
    } catch (error) {
      console.error("Error al cargar los candidatos:", error);
    }
  };

 /**
  * Función para manejar la eliminación de un candidato
  * Se solicita confirmación antes de proceder
  */
  const handleEliminarCandidato = async (id) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar este candidato?")) {
      try {
        await eliminarCandidato(id);
        setCandidatos(candidatos.filter((candidato) => candidato.id !== id));
      } catch (error) {
        console.error("Error al eliminar el candidato:", error);
      }
    }
  };

  // Filtra los candidatos buscando por nombre, apellidos, correo, perfil profesional y tecnologías
  const candidatosFiltrados = candidatos.filter((candidato) =>
    candidato.nombre.toLowerCase().includes(search.toLowerCase()) ||
    candidato.apellidos.toLowerCase().includes(search.toLowerCase()) ||
    candidato.correo.toLowerCase().includes(search.toLowerCase()) ||
    candidato.tipoPerfil?.nombre.toLowerCase().includes(search.toLowerCase()) || 
    candidato.tecnologias?.some(tecnologia => tecnologia.nombre.toLowerCase().includes(search.toLowerCase()))
  );

  // Muestra hasta 6 elementos por página resultado de la búsqueda
  const { paginatedData, currentPage, totalPages, nextPage, prevPage } = useTable(candidatosFiltrados, 6);

  return (
    <div className="container mt-4">
      {/* Encabezado de la página con el título y el enlace para añadir un candidato */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="text-primary fw-bold">Candidatos</h2>
        <Link to="/candidatos/agregar" className="btn btn-success">Añadir Candidato</Link>
      </div>

      {/* Barra de búsqueda para filtrar los candidatos */}
      <SearchBar search={search} setSearch={setSearch} placeholder="Buscar por nombre, apellidos, correo, perfil o tecnologías..." />

      <div className="row">
        {paginatedData.length > 0 ? (
          paginatedData.map((candidato) => (
            <div key={candidato.id} className="col-md-4">
              <div className="card shadow-sm mb-4 border-0 rounded-lg">
                <div className="card-body">
                  {/* Información del candidato */}
                  <h5 className="card-title text-primary">
                    <FaUser className="me-2" />
                    {candidato.nombre} {candidato.apellidos}
                  </h5>
                  <p className="card-text">
                    <FaEnvelope className="me-2 text-secondary" />
                    {candidato.correo}
                  </p>
                  <p className="card-text">
                    <FaPhone className="me-2 text-secondary" />
                    {candidato.telefono}
                  </p>
                  <p className="card-text">
                    <FaBriefcase className="me-2 text-secondary" />
                    <strong>Perfil:</strong> {candidato.tipoPerfil?.nombre || "No especificado"}
                  </p>
                  <p className="card-text">
                    <strong>Tecnologías:</strong> {candidato.tecnologias?.map(tec => tec.nombre).join(", ") || "N/A"}
                  </p>

                  {/* Botones de acción: Ver, Editar y Eliminar */}
                  <div className="d-flex justify-content-between">
                    <Link to={`/candidatos/ver/${candidato.id}`} className="btn btn-primary btn-sm">
                      <FaEye className="me-1" /> Ver
                    </Link>
                    <Link to={`/candidatos/editar/${candidato.id}`} className="btn btn-outline-primary btn-sm">
                      <FaEdit className="me-1" /> Editar
                    </Link>
                    <button className="btn btn-danger btn-sm" onClick={() => handleEliminarCandidato(candidato.id)}>
                      <FaTrash className="me-1" /> Eliminar
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="text-center mt-4">
            <h5 className="text-muted">No hay candidatos registrados</h5>
          </div>
        )}
      </div>
      {/* Componente de paginación */}
      <Pagination currentPage={currentPage} totalPages={totalPages} prevPage={prevPage} nextPage={nextPage} />
    </div>
  );
}
