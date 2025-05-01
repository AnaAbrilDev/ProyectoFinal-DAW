import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { obtenerUsuarios, eliminarUsuario } from "../services/usuarioService";
import useTable from "../../../hooks/useTable";
import SearchBar from "../../../components/SearchBar";
import Pagination from "../../../components/Pagination";
import "bootstrap/dist/css/bootstrap.min.css";

/**
 * Componente que muestra la lista de usuarios con opciones de búsqueda,
 * paginación, edición y eliminación.
 */
export default function ListadoUsuarios() {
  const [usuarios, setUsuarios] = useState([]); // Lista completa de usuarios
  const [search, setSearch] = useState("");  // Estado para la barra de búsqueda
  const userId = localStorage.getItem("userId"); // ID del usuario logueado para evitar que se elimine a sí mismo

  // Cargar usuarios al montar el componente
  useEffect(() => {
    loadUsuarios();
  }, []);

  /**
   * Obtiene la lista de usuarios desde el servicio y la almacena en el estado
   */
  const loadUsuarios = async () => {
    try {
      const result = await obtenerUsuarios();
      setUsuarios(result);
    } catch (error) {
      console.error("Error al cargar los usuarios:", error);
    }
  };

  /**
   * Elimina un usuario tras confirmar la acción mediante ventana emergente
   */
  const handleEliminarUsuario = async (id) => {
    if (window.confirm("¿Estás seguro de que quieres eliminar este usuario?")) {
      try {
        await eliminarUsuario(id);
        setUsuarios(usuarios.filter((usuario) => usuario.id !== id));
      } catch (error) {
        console.error("Error al eliminar el usuario:", error);
      }
    }
  };

  /**
   * Filtra la lista de usuarios según el valor de la barra de búsqueda
   */
  const usuariosFiltrados = usuarios.filter((usuario) =>
    usuario.nombre.toLowerCase().includes(search.toLowerCase()) ||
    usuario.apellidos.toLowerCase().includes(search.toLowerCase()) ||
    usuario.email.toLowerCase().includes(search.toLowerCase())
  );

  const { paginatedData, currentPage, totalPages, nextPage, prevPage } = useTable(usuariosFiltrados, 5);

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2 className="text-primary fw-bold">Usuarios</h2>
        <Link to="/usuarios/agregar" className="btn btn-success">Añadir Usuario</Link>
      </div>

      <SearchBar search={search} setSearch={setSearch} placeholder="Buscar usuarios por nombre, apellidos o email..." />

      <div className="row">
        {paginatedData.length > 0 ? (
          paginatedData.map((usuario) => (
            <div key={usuario.id} className="col-md-4 mb-3">
              <div className="card shadow-sm border-0 rounded-lg h-100">
                <div className="card-body">
                  <h5 className="card-title text-primary">{usuario.nombre} {usuario.apellidos}</h5>
                  <p className="card-text"><strong>Usuario:</strong> {usuario.username}</p>
                  <p className="card-text"><strong>Email:</strong> {usuario.email}</p>
                  <p className="card-text">
                    <strong>Roles:</strong> 
                    {usuario.roles.map((role, index) => (
                      <span key={index} className="badge bg-secondary ms-1">{role}</span>
                    ))}
                  </p>
                </div>
                <div className="card-footer bg-white d-flex justify-content-between">
                  <Link to={`/usuarios/ver/${usuario.id}`} className="btn btn-sm btn-outline-primary">Ver</Link>
                  {userId !== usuario.id.toString() && (
                    <div>
                      <Link to={`/usuarios/editar/${usuario.id}`} className="btn btn-sm btn-outline-warning me-2">Editar</Link>
                      <button className="btn btn-sm btn-outline-danger" onClick={() => handleEliminarUsuario(usuario.id)}>Eliminar</button>
                    </div>
                  )}
                </div>
              </div>
            </div>
          ))
        ) : (
          <div className="col-12 text-center">
            <p className="text-muted">No hay usuarios registrados</p>
          </div>
        )}
      </div>

      <Pagination currentPage={currentPage} totalPages={totalPages} prevPage={prevPage} nextPage={nextPage} />
    </div>
  );
}
