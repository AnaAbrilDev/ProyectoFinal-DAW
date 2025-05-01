import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { agregarUsuario } from "../services/usuarioService";

/**
 * Componente para añadir un nuevo usuario al sistema.
 * Permite rellenar los campos del usuario, asignar roles y enviar los datos al backend
 */

export default function AgregarUsuario() {
  const navigate = useNavigate(); // Hook para navegar entre rutas
  const [error, setError] = useState(""); // Estado para manejar mensajes de error
  const [usuario, setUsuario] = useState({
    username: "",
    nombre: "",
    apellidos: "",
    email: "",
    password: "",
    roles: [], // Lista de roles seleccionados
  });

  /**
   * Maneja los cambios en los inputs del formulario
   * Actualiza el estado del usuario según los campos modificados
   */
  const handleChange = (e) => {
    setUsuario({
      ...usuario,
      [e.target.name]: e.target.value,
    });
  };

  /**
   * Maneja la selección o deselección de roles mediante checkboxes
   * Si el checkbox está seleccionado, añade el rol al estado, si no, lo elimina.
   */
  const handleRolesChange = (e) => {
    const { value, checked } = e.target;
    setUsuario((prevState) => ({
      ...prevState,
      roles: checked
        ? [...prevState.roles, { id: parseInt(value) }]
        : prevState.roles.filter((role) => role.id !== parseInt(value)),
    }));
  };

  /**
   * Maneja el envío del formulario para agregar un nuevo usuario.
   * Envía los datos al backend y navega de vuelta al listado de usuarios si tiene éxito
   */
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevenir la recarga de la página
    setError(""); // Limpiar el mensaje de error antes de intentar enviar los datos

    try {
      await agregarUsuario(usuario); // Llamada al servicio para agregar el usuario
      navigate("/usuarios"); // Redirigir al listado de usuarios tras agregar correctamente
    } catch (error) {
      console.error("Error al agregar usuario:", error);
      if (error.response?.data?.error) {
        setError(error.response.data.error);
      } else {
        setError("Error desconocido al agregar usuario.");
      }
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-sm border-0 rounded-lg">
            <div className="card-body">
              <h3 className="text-center text-primary fw-bold mb-3">Agregar Usuario</h3>

              {error && <div className="alert alert-danger text-center">{error}</div>}

              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">Usuario</label>
                  <input type="text" name="username" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Nombre</label>
                  <input type="text" name="nombre" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Apellidos</label>
                  <input type="text" name="apellidos" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Email</label>
                  <input type="email" name="email" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Contraseña</label>
                  <input type="password" name="password" className="form-control" onChange={handleChange} required />
                </div>

                {/* Checkbox de Roles */}
                <div className="mb-3">
                  <label className="form-label">Roles</label>
                  <div className="d-flex gap-3">
                    <div className="form-check">
                      <input className="form-check-input" type="checkbox" id="coordinador" value="1" onChange={handleRolesChange} />
                      <label className="form-check-label" htmlFor="coordinador">Coordinador</label>
                    </div>
                    <div className="form-check">
                      <input className="form-check-input" type="checkbox" id="recruiter" value="2" onChange={handleRolesChange} />
                      <label className="form-check-label" htmlFor="recruiter">Recruiter</label>
                    </div>
                  </div>
                </div>

                <div className="text-center">
                  <button type="submit" className="btn btn-primary w-100">Guardar Usuario</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
