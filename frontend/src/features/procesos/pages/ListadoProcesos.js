import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { obtenerProcesos, eliminarProceso } from "../services/procesoService";
import useTable from "../../../hooks/useTable"; // Hook para gestionar paginación
import SearchBar from "../../../components/SearchBar"; // Componente para la búsqueda
import Pagination from "../../../components/Pagination"; // Componente para paginación
import { FaUserTie, FaClipboardList, FaTrashAlt, FaEdit } from "react-icons/fa"; // Iconos pata mejorar la UI

/**
 * Componente para listar los procesos de selección
 * Incluye opciones para filtrar, paginar y gestionar procesos (ver, editar, eliminar)
 */

export default function ListadoProcesos() {
  // Estado para almacenar los procesos
  const [procesos, setProcesos] = useState([]);
  const [search, setSearch] = useState(""); // Filtro de búsqueda
  const [roles, setRoles] = useState([]); // Roles del usuario
  const [userId, setUserId] = useState(null); // ID del usuario logueado

  // Efecto que se ejecuta al montar el componente para cargar los datos iniciales
  useEffect(() => {
    const storedRoles = JSON.parse(localStorage.getItem("roles")) || [];
    const storedUserId = localStorage.getItem("userId");

    setRoles(storedRoles);
    setUserId(parseInt(storedUserId));
    cargarProcesos();
  }, []);

  /**
   * Carga la lista de procesos desde el servicio correspondiente
   */
  const cargarProcesos = async () => {
    try {
      const result = await obtenerProcesos();
      setProcesos(result);
    } catch (error) {
      console.error("Error al cargar los procesos:", error);
    }
  };

  /**
   * Maneja la eliminación de un proceso tras confirmar con el usuario
   */
  const handleEliminarProceso = async (id) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar este proceso?")) {
      try {
        await eliminarProceso(id);
        setProcesos(procesos.filter((proceso) => proceso.id !== id)); // Elimina del estado el proceso eliminado
      } catch (error) {
        console.error("Error al eliminar el proceso:", error);
      }
    }
  };

  // Verifica si el usuario solo tiene el rol de Recruiter
  const esSoloRecruiter = roles.length === 1 && roles.includes("RECRUITER");

  /**
   * Filtra los procesos según el texto de búsqueda y, si el usuario es recruiter, filtra solo los procesos que le pertenecen
   */
  const procesosFiltrados = procesos.filter((proceso) => {
    const cumpleBusqueda =
    (proceso.sectorProyecto && proceso.sectorProyecto.toLowerCase().includes(search.toLowerCase())) ||
    (proceso.estado && proceso.estado.toLowerCase().includes(search.toLowerCase())) ||
    (proceso.usuario && proceso.usuario.nombre.toLowerCase().includes(search.toLowerCase())) ||
    (proceso.tecnologias && proceso.tecnologias.some((tech) => tech.nombre.toLowerCase().includes(search.toLowerCase()))) ||
    (proceso.tipoPerfil && proceso.tipoPerfil.nombre.toLowerCase().includes(search.toLowerCase())) ||
    (proceso.modalidadProvincia && proceso.modalidadProvincia.provincia.toLowerCase().includes(search.toLowerCase()));

    if (esSoloRecruiter) {
      return cumpleBusqueda && proceso.usuario.id === userId;
    }

    return cumpleBusqueda;
  });

  // Paginación usando el hook personalizado useTable
  const { paginatedData, currentPage, totalPages, nextPage, prevPage } = useTable(procesosFiltrados, 6);

  return (
    <div className="container mt-4">
      {/* Encabezado con el título y el botón para agregar proceso */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="text-primary fw-bold">Procesos de Selección</h2>
        {!esSoloRecruiter && (
          <Link to="/procesos/agregar" className="btn btn-success">Añadir Proceso</Link>
        )}
      </div>
      
      {/* Barra de búsqueda */}
      <SearchBar search={search} setSearch={setSearch} placeholder="Buscar procesos..." />

      {/* Listado de procesos */}
      {paginatedData.length > 0 ? (
        <div className="row">
          {paginatedData.map((proceso) => (
            <div key={proceso.id} className="col-lg-4 col-md-6 mb-4">
              <div className="card shadow-sm border-0">
                <div className="card-body">
                  <h5 className="card-title text-dark">{proceso.sectorProyecto}</h5>
                  <p className="card-text text-muted">{proceso.descripcion}</p>
                  
                  <div>
                    <span className={`badge ${proceso.estado === "ACTIVO" ? "bg-success" : "bg-danger"} me-2`}>
                      {proceso.estado}
                    </span>
                    <span className={`badge ${proceso.prioridad === "ALTA" ? "bg-warning text-dark" : "bg-secondary"} me-2`}>
                      {proceso.prioridad}
                    </span>
                  </div>

                  <p className="mt-2"><strong>Usuario:</strong> {proceso.usuario.nombre} {proceso.usuario.apellidos}</p>
                  
                  <p><strong>Perfil:</strong> {proceso.tipoPerfil.nombre}</p>

                  <p><strong>Modalidad:</strong> {proceso.modalidadProvincia.provincia} - {proceso.modalidadProvincia.modalidad}</p>

                  <div className="mb-2">
                    <strong>Tecnologías:</strong>
                    <div className="mt-1">
                      {proceso.tecnologias.length > 0 ? (
                        proceso.tecnologias.map((tech, index) => (
                          <span key={index} className="badge bg-primary me-2">{tech.nombre}</span>
                        ))
                      ) : (
                        <span className="text-muted">No especificado</span>
                      )}
                    </div>
                  </div>

                  <div className="d-flex justify-content-between">
                    <Link to={`/procesos/ver/${proceso.id}`} className="btn btn-primary btn-sm">
                      <FaClipboardList className="me-1" /> Ver
                    </Link>

                    {!esSoloRecruiter && (
                      <>
                        <Link to={`/procesos/editar/${proceso.id}`} className="btn btn-outline-primary btn-sm">
                          <FaEdit className="me-1" /> Editar
                        </Link>
                        <button className="btn btn-danger btn-sm" onClick={() => handleEliminarProceso(proceso.id)}>
                          <FaTrashAlt className="me-1" /> Eliminar
                        </button>
                      </>
                    )}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="alert alert-info text-center">No hay procesos registrados.</div>
      )}

      <Pagination currentPage={currentPage} totalPages={totalPages} prevPage={prevPage} nextPage={nextPage} />
    </div>
  );
}
