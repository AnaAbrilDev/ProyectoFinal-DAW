import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { obtenerCandidaturas, eliminarCandidatura } from "../services/candidaturaService";
import useTable from "../../../hooks/useTable"; // Hook personalizado para paginación
import SearchBar from "../../../components/SearchBar"; // Componente de barra de búsqueda
import Pagination from "../../../components/Pagination"; // Componente de paginación
import "bootstrap/dist/css/bootstrap.min.css"; // Estilos de Bootstrap

// Componente principal para mostrar el listado de candidaturas
export default function ListadoCandidaturas() {
  // Estado para almacenar las candidaturas, búsqueda, roles y el ID del usuario
  const [candidaturas, setCandidaturas] = useState([]);
  const [search, setSearch] = useState("");
  const [roles, setRoles] = useState([]);
  const [userId, setUserId] = useState(null);

  // Cargar información del localStorage y obtener candidaturas al montar el componente
  useEffect(() => {
    const storedRoles = JSON.parse(localStorage.getItem("roles")) || [];
    const storedUserId = localStorage.getItem("userId");

    setRoles(storedRoles);
    setUserId(parseInt(storedUserId)); // Convertir el ID a número para garantizar la comparación correcta
    cargarCandidaturas();
  }, []);

  // Obtiene las candidaturas del servicio y las guarda en el estado
  const cargarCandidaturas = async () => {
    try {
      const result = await obtenerCandidaturas();
      setCandidaturas(result);
    } catch (error) {
      console.error("Error al cargar las candidaturas:", error);
    }
  };

  // Elimina una candidatura previa confirmación del usuario
  const handleEliminarCandidatura = async (id) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar esta candidatura?")) {
      try {
        await eliminarCandidatura(id);
        setCandidaturas(candidaturas.filter((candidatura) => candidatura.id !== id));
      } catch (error) {
        console.error("Error al eliminar la candidatura:", error);
      }
    }
  };

  // Vertifica si el usuario tiene únicamente el rol de Recruiter
  const esSoloRecruiter = roles.length === 1 && roles.includes("RECRUITER");

  // Filtra las candidaturas según el texto de búsqueda y los permisos del usuario
  const candidaturasFiltradas = candidaturas.filter((candidatura) => {
    const cumpleBusqueda =
      candidatura.candidato.nombre.toLowerCase().includes(search.toLowerCase()) ||
      candidatura.candidato.apellidos.toLowerCase().includes(search.toLowerCase()) ||
      candidatura.procesoSeleccion.sectorProyecto.toLowerCase().includes(search.toLowerCase()) ||
      candidatura.historialEstados.some((estado) => estado.estado.toLowerCase().includes(search.toLowerCase()));

    if (esSoloRecruiter) {
      return cumpleBusqueda && candidatura.procesoSeleccion.usuario.id === userId;
    }

    return cumpleBusqueda;
  });

  // Utiliza el hook personalizado para gestionar la paginación
  const { paginatedData, currentPage, totalPages, nextPage, prevPage } = useTable(candidaturasFiltradas, 5);

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="text-primary fw-bold">Candidaturas</h2>
      
          <Link to="/candidaturas/agregar" className="btn btn-success">Nueva Candidatura</Link>
        
      </div>

      <SearchBar search={search} setSearch={setSearch} placeholder="Buscar por candidato, proceso o estado..." />

      <div className="card shadow-sm">
        <div className="card-body">
          <table className="table table-hover">
            <thead className="table-light">
              <tr>
                <th>ID</th>
                <th>Candidato</th>
                <th>Proceso</th>
                <th>Estado</th>
                <th>Fecha Creación</th>
                <th>Acción</th>
              </tr>
            </thead>
            <tbody>
              {paginatedData.length > 0 ? (
                paginatedData.map((candidatura) => {
                  const ultimoEstado = candidatura.historialEstados.length > 0 
                    ? [...candidatura.historialEstados].sort((a, b) => new Date(b.fechaCambio) - new Date(a.fechaCambio))[0].estado 
                    : "Sin estado";

                  return (
                    <tr key={candidatura.id}>
                      <td>{candidatura.id}</td>
                      <td>{candidatura.candidato.nombre} {candidatura.candidato.apellidos}</td>
                      <td>{candidatura.procesoSeleccion.sectorProyecto}</td>
                      <td>
                        <span className={`badge ${ultimoEstado === "NUEVA" ? "bg-info" : "bg-secondary"}`}>
                          {ultimoEstado}
                        </span>
                      </td>
                      <td>{new Date(candidatura.fechaCreacion).toLocaleDateString()}</td>
                      <td>
                        <div className="btn-group">
                          <Link to={`/candidaturas/ver/${candidatura.id}`} className="btn btn-primary btn-sm me-2">Ver</Link>
                          {!esSoloRecruiter && (
                            <button className="btn btn-danger btn-sm" onClick={() => handleEliminarCandidatura(candidatura.id)}>
                              Eliminar
                            </button>
                          )}
                        </div>
                      </td>
                    </tr>
                  );
                })
              ) : (
                <tr>
                  <td colSpan="6" className="text-center">No hay candidaturas registradas</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

      <Pagination currentPage={currentPage} totalPages={totalPages} prevPage={prevPage} nextPage={nextPage} />
    </div>
  );
}
