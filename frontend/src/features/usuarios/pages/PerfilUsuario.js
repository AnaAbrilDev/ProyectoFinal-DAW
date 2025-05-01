import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { obtenerUsuarioPorId, editarUsuario } from "../services/usuarioService";

/**
 * Componente que gestiona la visualización y edición del perfil de usuario.
 * Permite actualizar datos personales y cambiar la contraseña.
 */
export default function PerfilUsuario() {
  const { id } = useParams(); // ID del usuario obtenido desde la URL
  const navigate = useNavigate(); // Navegación programática
  const userId = localStorage.getItem("userId"); // ID del usuario loqueado

  // Estado para almacenar la información del usuario
  const [usuario, setUsuario] = useState({
    username: "",
    nombre: "",
    apellidos: "",
    email: "",
    password: "",
    confirmPassword: "",
    roles: [],
  });

  // Estados para manejar errores y mensajes de éxito
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  /**
   * Efecto que carga el perfil del usuario al montar el componente
   * También redirige si el usuario intenta acceder a otro perfil que no sea el suyo
   */
  useEffect(() => {
    if (userId !== id) {
      navigate(`/usuarios/perfil/${userId}`); 
      return;
    }
    cargarUsuario();
  }, [id, userId, navigate]);

  /**
   * Carga los datos del usuario desde el servicio y los almacena en el estado
   */
  const cargarUsuario = async () => {
    try {
      const data = await obtenerUsuarioPorId(id);

      // Se limpian los campos de contraseña por seguridad
      setUsuario({ ...data, password: "", confirmPassword: "" });
    } catch (error) {
      setError("Error al cargar el perfil.");
    }
  };

  /**
   * Maneja el cambio en los campos del formulario y actualiza el estado del usuario
   */
  const handleChange = (e) => {
    setUsuario({
      ...usuario,
      [e.target.name]: e.target.value,
    });
  };

  /**
   * Envía la información actualizada del usuario al servicio de edición
   * Valida que ambas contraseñas coincidan si se proporcionan
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    if (usuario.password && usuario.password !== usuario.confirmPassword) {
      setError("Las contraseñas no coinciden.");
      return;
    }

    try {
      const userData = { ...usuario };
      if (!usuario.password) delete userData.password;
      delete userData.confirmPassword;
      delete userData.roles;

      await editarUsuario(id, userData);
      setSuccess("Perfil actualizado correctamente.");
      cargarUsuario();
    } catch (error) {
      setError("Error al actualizar el perfil.");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-lg-8">
          <div className="card shadow-sm p-4">
            <h3 className="text-center mb-4">Perfil de Usuario</h3>

            {error && <div className="alert alert-danger">{error}</div>}
            {success && <div className="alert alert-success">{success}</div>}

            <form onSubmit={handleSubmit}>
              {/* Información del Usuario */}
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Usuario</label>
                  <input type="text" name="username" className="form-control" value={usuario.username} disabled />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label">Email</label>
                  <input type="email" name="email" className="form-control" value={usuario.email} onChange={handleChange} required />
                </div>
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Nombre</label>
                  <input type="text" name="nombre" className="form-control" value={usuario.nombre} onChange={handleChange} required />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label">Apellidos</label>
                  <input type="text" name="apellidos" className="form-control" value={usuario.apellidos} onChange={handleChange} required />
                </div>
              </div>

              {/* Cambio de Contraseña */}
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Nueva Contraseña</label>
                  <input type="password" name="password" className="form-control" value={usuario.password} onChange={handleChange} />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label">Confirmar Contraseña</label>
                  <input type="password" name="confirmPassword" className="form-control" value={usuario.confirmPassword} onChange={handleChange} />
                </div>
              </div>

              {/* Roles del Usuario */}
              <div className="mb-3">
                <label className="form-label">Roles</label>
                <div>
                  {usuario.roles.map((role, index) => (
                    <span key={index} className="badge bg-primary me-2">{role}</span>
                  ))}
                </div>
              </div>

              <div className="text-center">
                <button type="submit" className="btn btn-primary px-4">Actualizar</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
