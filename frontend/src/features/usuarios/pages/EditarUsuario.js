import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { obtenerUsuarioPorId, editarUsuario } from "../services/usuarioService";

/**
 * Componente para editar la información de un usuario.
 * Permite modificar datos personales, roles y contraseña (opcional).
 */
export default function EditarUsuario() {
  const { id } = useParams(); // Obtiene el ID del usuario desde la URL
  const navigate = useNavigate(); // Permite redirigir tras la edición

  // Estado para almacenar el usuario y gestionar errores
  const [error, setError] = useState("");
  const [usuario, setUsuario] = useState({
    username: "",
    nombre: "",
    apellidos: "",
    email: "",
    password: "", // Se deja en blanco por defecto (solo se actualizará si se introduce algo)
    roles: [],
  });

  // Carga la información del usuario al montar el componente
  useEffect(() => {
    loadUsuario();
  }, []);

  /**
   * Carga los datos del usuario desde el servicio y adapta los roles al formato esperado
   */
  const loadUsuario = async () => {
    try {
      const data = await obtenerUsuarioPorId(id);

      // Mapa de roles para convertir roles del backend en el formato esperado
      const rolesMap = {
        COORDINADOR: { id: 1 },
        RECRUITER: { id: 2 },
      };

      // Convierte los roles del usuario al formato requerido
      const rolesConvertidos = data.roles
        ? data.roles.map((rol) => rolesMap[rol]).filter(rol => rol)
        : [];

      setUsuario({
        ...data,
        roles: rolesConvertidos,
      });
    } catch (error) {
      console.error("Error al obtener usuario:", error);
      setError("No se pudo cargar la información del usuario.");
    }
  };

  const handleChange = (e) => {
    setUsuario({
      ...usuario,
      [e.target.name]: e.target.value,
    });
  };

  const handleRolesChange = (e) => {
    const { value, checked } = e.target;
    setUsuario((prevState) => ({
      ...prevState,
      roles: checked
        ? [...prevState.roles, { id: parseInt(value) }]
        : prevState.roles.filter((role) => role.id !== parseInt(value)),
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      await editarUsuario(id, usuario);
      navigate("/usuarios");
    } catch (error) {
      console.error("Error al editar usuario:", error);
      setError("No se pudo actualizar el usuario.");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-sm border-0 rounded-lg">
            <div className="card-body">
              <h3 className="text-center text-primary fw-bold mb-3">Editar Usuario</h3>

              {error && <div className="alert alert-danger text-center">{error}</div>}

              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">Usuario</label>
                  <input type="text" name="username" className="form-control" value={usuario.username || ""} onChange={handleChange} disabled />
                </div>
                <div className="mb-3">
                  <label className="form-label">Nombre</label>
                  <input type="text" name="nombre" className="form-control" value={usuario.nombre || ""} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Apellidos</label>
                  <input type="text" name="apellidos" className="form-control" value={usuario.apellidos || ""} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Email</label>
                  <input type="email" name="email" className="form-control" value={usuario.email || ""} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Nueva Contraseña</label>
                  <input type="password" name="password" className="form-control" placeholder="(Opcional: deja en blanco si no quieres cambiarla)" onChange={handleChange} />
                </div>

                {/* Checkboxes de Roles */}
                <div className="mb-3">
                  <label className="form-label">Roles</label>
                  <div className="d-flex gap-3">
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="coordinador"
                        value="1"
                        checked={usuario.roles.some((rol) => rol.id === 1)}
                        onChange={handleRolesChange}
                      />
                      <label className="form-check-label" htmlFor="coordinador">Coordinador</label>
                    </div>
                    <div className="form-check">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="recruiter"
                        value="2"
                        checked={usuario.roles.some((rol) => rol.id === 2)}
                        onChange={handleRolesChange}
                      />
                      <label className="form-check-label" htmlFor="recruiter">Recruiter</label>
                    </div>
                  </div>
                </div>

                <div className="text-center">
                  <button type="submit" className="btn btn-primary w-100">Actualizar Usuario</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
