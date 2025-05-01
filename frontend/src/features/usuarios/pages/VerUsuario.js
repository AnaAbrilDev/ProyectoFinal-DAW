import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // Obtiene parámetros de la URL
import { obtenerUsuarioPorId } from "../services/usuarioService"; // Servicio para obtener datos del usuario
import { FaUser, FaEnvelope, FaUserShield, FaIdBadge } from "react-icons/fa"; // Iconos para mayor claridad visual

/**
 * Componente que muestra los detalles de un usuario específico
 */
export default function VerUsuario() {
  const { id } = useParams(); // Obtiene el ID del usuario desde la URL
  const [usuario, setUsuario] = useState(null); // Estado para almacenar la información del usuario

  /**
   * Efecto que carga el usuario al montar el componente
   */
  useEffect(() => {
    loadUsuario();
  }, []);

  /**
   * Carga la información del usuario según su ID
   */
  const loadUsuario = async () => {
    try {
      const data = await obtenerUsuarioPorId(id);
      setUsuario(data); // Actualiza el estado con la información del usuario
    } catch (error) {
      console.error("Error al obtener usuario:", error);
    }
  };

  // Mostrar un mensaje mientras se cargan los datos
  if (!usuario) {
    return (
      <div className="container mt-4 text-center">
        <h3 className="text-muted">Cargando usuario...</h3>
      </div>
    );
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-lg border-0 rounded-lg">
            <div className="card-body">
              <h3 className="text-center text-primary fw-bold mb-3">Detalles del Usuario</h3>

              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <FaIdBadge className="me-2 text-secondary" />
                  <strong>ID:</strong> {usuario.id}
                </li>
                <li className="list-group-item">
                  <FaUser className="me-2 text-secondary" />
                  <strong>Usuario:</strong> {usuario.username}
                </li>
                <li className="list-group-item">
                  <FaUser className="me-2 text-secondary" />
                  <strong>Nombre:</strong> {usuario.nombre} {usuario.apellidos}
                </li>
                <li className="list-group-item">
                  <FaEnvelope className="me-2 text-secondary" />
                  <strong>Email:</strong> {usuario.email}
                </li>
                <li className="list-group-item">
                  <FaUserShield className="me-2 text-secondary" />
                  <strong>Roles:</strong> {usuario.roles?.length > 0 ? usuario.roles.join(", ") : "Sin roles asignados"}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
