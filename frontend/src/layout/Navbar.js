import React, { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom"; // Gestiona rutas y navegación
import { FaUserCircle, FaSignOutAlt } from "react-icons/fa"; // Iconos para mejorar la interfaz visual
import "bootstrap/dist/css/bootstrap.min.css"; // Estilos de Bootstrap

/**
 * Componente que renderiza la barra de navegación de la aplicación
 */
export default function Navbar() {
  const location = useLocation(); // Obtiene la URL actual
  const navigate = useNavigate(); // Permite redirigir a otras rutas
  const [roles, setRoles] = useState([]); // Estado para almacenar los roles del usuario
  const [userId, setUserId] = useState(null); // Estado para almacenar el ID del usuario

  /**
   * Al montar el componente, recupera roles e ID del usuario desde el almacenamiento local
   */
  useEffect(() => {
    const storedRoles = localStorage.getItem("roles");
    const storedUserId = localStorage.getItem("userId");

    if (storedRoles) {
      setRoles(JSON.parse(storedRoles));
    }
    if (storedUserId) {
      setUserId(storedUserId);
    }
  }, []);

  /**
   * Determina si el usuario tiene el rol de Coordinador
   */
  const isCoordinador = roles.includes("COORDINADOR");

  /**
   * Define las opciones del menú según los roles del usuario
   */
  const menuItems = [
    ...(isCoordinador ? [{ path: "/usuarios", label: "Usuarios" }] : []), // Solo los coordinadores ven esta opción
    { path: "/candidatos", label: "Candidatos" },
    { path: "/procesos", label: "Procesos de selección" },
    { path: "/candidaturas", label: "Candidaturas" },
  ];

  /**
   * Cierra sesión del usuario tras confirmación
   * Elimina el token, roles e ID del usuario del almacenamiento local
   */
  const handleLogout = () => {
    if (window.confirm("¿Estás seguro de que quieres cerrar sesión?")) {
      localStorage.removeItem("token");
      localStorage.removeItem("roles");
      localStorage.removeItem("userId");
      navigate("/login"); // Redirige a la página de inicio de sesión
    }
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
      <div className="container">
        <Link className="navbar-brand fw-bold text-primary" to="/home">
          WebTrack
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse justify-content-between" id="navbarNav">
          <ul className="navbar-nav">
            {menuItems.map((item) => (
              <li key={item.path} className="nav-item">
                <Link
                  className={`nav-link ${location.pathname === item.path ? "active text-primary fw-bold" : ""}`}
                  to={item.path}
                >
                  {item.label}
                </Link>
              </li>
            ))}
          </ul>

          <div className="d-flex align-items-center">
            {userId && (
              <Link className="btn btn-outline-primary me-3" to={`/usuarios/perfil/${userId}`}>
                <FaUserCircle className="me-1" /> Perfil
              </Link>
            )}

            <button className="btn btn-danger" onClick={handleLogout}>
              <FaSignOutAlt className="me-1" /> Cerrar sesión
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
}
