import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // Hook para redireccionar tras finalizar una acción
import { 
  agregarProceso, 
  obtenerPerfiles, 
  obtenerTecnologias, 
  obtenerModalidadesProvincia, 
  obtenerUsuarios
} from "../services/procesoService"; // Servicios para obtener y gestionar datos relacionados con procesos de selección

/**
 * Componente que permite agregar un nuevo proceso de selección
 * Gestiona la carga de datos necesarios y el envío del formulario
 */

export default function AgregarProceso() {
  const navigate = useNavigate(); // Hook para redirigir a otra página
  const [proceso, setProceso] = useState({
    sectorProyecto: "",
    estado: "ACTIVO",
    prioridad: "MEDIA",
    modalidadProvincia: "",
    tipoPerfil: "",
    usuario: "",
    descripcion: "",
    salario: "",
    tecnologias: [],
  });

  // Estados para almacenar datos obtenidos del backend
  const [perfiles, setPerfiles] = useState([]);
  const [tecnologias, setTecnologias] = useState([]);
  const [modalidadesProvincia, setModalidadesProvincia] = useState([]);
  const [usuarios, setUsuarios] = useState([]);

  // Control de errores y carga
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  // Carga de datos al montar el componente
  useEffect(() => {
    cargarDatos();
  }, []);

  /**
   * Carga los datos necesarios para los selectores del formulario
   */
  const cargarDatos = async () => {
    try {
      setPerfiles(await obtenerPerfiles());
      setTecnologias(await obtenerTecnologias());
      setModalidadesProvincia(await obtenerModalidadesProvincia());

      // Obtener usuarios y filtrar solo los que tienen el rol "RECRUITER"
      const allUsuarios = await obtenerUsuarios();
      const recruiters = allUsuarios.filter(usuario => usuario.roles.includes("RECRUITER"));
      setUsuarios(recruiters);
    } catch (error) {
      setError("Error al cargar datos.");
    }
  };

  /**
   * Maneja cambios en los campos del formulario
   */
  const handleChange = (e) => {
    setProceso({
      ...proceso,
      [e.target.name]: e.target.value,
    });
  };

  /**
   * Maneja los cambios en los selectores que almacenan objetos 
   */
  const handleSelectChange = (e) => {
    setProceso({
      ...proceso,
      [e.target.name]: { id: parseInt(e.target.value) },
    });
  };

  /**
   * Maneja los cambios en el selector múltiple de tecnologías 
   */
  const handleTecnologiasChange = (e) => {
    const selectedTecnologias = Array.from(e.target.selectedOptions).map(option => ({
      id: parseInt(option.value),
      nombre: option.text,
    }));
    setProceso({
      ...proceso,
      tecnologias: selectedTecnologias,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    // Crear una copia del proceso eliminando el nombre de las tecnologías
    const procesoParaEnviar = {
      ...proceso,
      tecnologias: proceso.tecnologias.map(tech => ({ id: tech.id })), // Solo ID
    };

    try {
      await agregarProceso(procesoParaEnviar);
      navigate("/procesos");
    } catch (error) {
      setError("Error al agregar proceso.");
    } finally {
      setLoading(false);
    }
};

  return (
    <div className="container mt-4">
      <h2 className="text-primary">Agregar Proceso de Selección</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="row">
          <div className="col-md-6 mb-3">
            <label className="form-label">Sector del Proyecto</label>
            <input type="text" name="sectorProyecto" className="form-control" placeholder="Ej. Desarrollo Web" onChange={handleChange} required />
          </div>

          <div className="col-md-3 mb-3">
            <label className="form-label">Estado</label>
            <select name="estado" className="form-control" onChange={handleChange} required>
              <option value="ACTIVO">Activo</option>
              <option value="LOGRADO">Logrado</option>
              <option value="EN_GESTION">En gestión</option>
              <option value="CERRADO">Cerrado</option>
            </select>
          </div>

          <div className="col-md-3 mb-3">
            <label className="form-label">Prioridad</label>
            <select name="prioridad" className="form-control" onChange={handleChange} required>
              <option value="ALTA">Alta</option>
              <option value="MEDIA">Media</option>
              <option value="BAJA">Baja</option>
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Modalidad Provincia</label>
            <select name="modalidadProvincia" className="form-control" onChange={handleSelectChange} required>
              <option value="">Seleccione una modalidad</option>
              {modalidadesProvincia.map((modalidad) => (
                <option key={modalidad.id} value={modalidad.id}>
                  {modalidad.provincia} - {modalidad.modalidad}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Perfil Profesional</label>
            <select name="tipoPerfil" className="form-control" onChange={handleSelectChange} required>
              <option value="">Seleccione un perfil</option>
              {perfiles.map((perfil) => (
                <option key={perfil.id} value={perfil.id}>{perfil.nombre}</option>
              ))}
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Usuario Responsable (Recruiter)</label>
            <select name="usuario" className="form-control" onChange={handleSelectChange} required>
              <option value="">Seleccione un usuario</option>
              {usuarios.map((usuario) => (
                <option key={usuario.id} value={usuario.id}>
                  {usuario.nombre} {usuario.apellidos} ({usuario.username})
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Salario</label>
            <input type="number" name="salario" className="form-control" placeholder="Ej. 45000" onChange={handleChange} required />
          </div>

          <div className="col-md-12 mb-3">
            <label className="form-label">Tecnologías</label>
            <select name="tecnologias" className="form-control" multiple onChange={handleTecnologiasChange} required>
              {tecnologias.map((tech) => (
                <option key={tech.id} value={tech.id}>{tech.nombre}</option>
              ))}
            </select>
            <div className="mt-2">
              {proceso.tecnologias.map((tech) => (
                <span key={tech.id} className="badge bg-primary me-2">{tech.nombre}</span>
              ))}
            </div>
          </div>

          <div className="col-md-12 mb-3">
            <label className="form-label">Descripción</label>
            <textarea name="descripcion" className="form-control" placeholder="Descripción del proceso..." onChange={handleChange} required />
          </div>
        </div>

        <button type="submit" className="btn btn-success" disabled={loading}>
          {loading ? "Guardando..." : "Guardar"}
        </button>
      </form>
    </div>
  );
}
