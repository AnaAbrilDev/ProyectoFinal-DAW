import React, { useEffect, useState } from "react"; // Importa React y hooks necesarios para el manejo de estado y efectos 
import { useNavigate, useParams } from "react-router-dom"; // Navegación y acceso a parámetros de la URL
import { 
  obtenerProcesoPorId, 
  editarProceso, 
  obtenerPerfiles, 
  obtenerTecnologias, 
  obtenerModalidadesProvincia, 
  obtenerUsuarios 
} from "../services/procesoService"; // Importa funciones para realizar peticiones al backend

/**
 * Componente para editar los detalles de un proceso de selección
 */

export default function EditarProceso() {
  const { id } = useParams(); // Obtiene el ID del proceso desde la URL
  const navigate = useNavigate(); 
  
  // Estado inicial del formulario
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

  // Estados para almacenar listas obtenidas del backend
  const [perfiles, setPerfiles] = useState([]);
  const [tecnologias, setTecnologias] = useState([]);
  const [modalidadesProvincia, setModalidadesProvincia] = useState([]);
  const [usuarios, setUsuarios] = useState([]);
  // Estado para manejar mensajes de error y la carga del formulario
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  // Se ejecuta al cargar el componente para obtener los datos iniciales
  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const procesoData = await obtenerProcesoPorId(id);
      console.log(procesoData);
      
      setProceso({
        ...procesoData,
        modalidadProvincia: { id: procesoData.modalidadProvincia?.id || "" },
        tipoPerfil: { id: procesoData.tipoPerfil?.id || "" },
        usuario: { id: procesoData.usuario?.id || "" },
        tecnologias: procesoData.tecnologias.map((tech) => ({
          id: tech.id,
          nombre: tech.nombre,
        })),
      });

      setPerfiles(await obtenerPerfiles());
      setTecnologias(await obtenerTecnologias());
      setModalidadesProvincia(await obtenerModalidadesProvincia());

      // Filtrar usuarios solo con rol "RECRUITER"
      const allUsuarios = await obtenerUsuarios();
      setUsuarios(allUsuarios.filter(usuario => usuario.roles.includes("RECRUITER")));
    } catch (error) {
      setError("Error al cargar datos.");
    }
  };

  const handleChange = (e) => {
    setProceso({
      ...proceso,
      [e.target.name]: e.target.value,
    });
  };

  const handleSelectChange = (e) => {
    setProceso({
      ...proceso,
      [e.target.name]: { id: parseInt(e.target.value) },
    });
  };

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
      console.log("Enviando datos actualizados:", procesoParaEnviar);
      await editarProceso(id, procesoParaEnviar);
      navigate("/procesos");
    } catch (error) {
      setError("Error al actualizar proceso.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-primary">Editar Proceso de Selección</h2>

      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="row">
          <div className="col-md-6 mb-3">
            <label className="form-label">Sector del Proyecto</label>
            <input type="text" name="sectorProyecto" className="form-control" value={proceso.sectorProyecto} onChange={handleChange} required />
          </div>

          <div className="col-md-3 mb-3">
            <label className="form-label">Estado</label>
            <select name="estado" className="form-control" value={proceso.estado} onChange={handleChange} required>
              <option value="ACTIVO">Activo</option>
              <option value="LOGRADO">Logrado</option>
              <option value="EN_GESTION">En gestión</option>
              <option value="CERRADO">Cerrado</option>
            </select>
          </div>

          <div className="col-md-3 mb-3">
            <label className="form-label">Prioridad</label>
            <select name="prioridad" className="form-control" value={proceso.prioridad} onChange={handleChange} required>
              <option value="ALTA">Alta</option>
              <option value="MEDIA">Media</option>
              <option value="BAJA">Baja</option>
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Modalidad Provincia</label>
            <select name="modalidadProvincia" className="form-control" value={proceso.modalidadProvincia.id} onChange={handleSelectChange} required>
              {modalidadesProvincia.map((modalidad) => (
                <option key={modalidad.id} value={modalidad.id}>
                  {modalidad.provincia} - {modalidad.modalidad}
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Perfil Profesional</label>
            <select name="tipoPerfil" className="form-control" value={proceso.tipoPerfil.id} onChange={handleSelectChange} required>
              {perfiles.map((perfil) => (
                <option key={perfil.id} value={perfil.id}>{perfil.nombre}</option>
              ))}
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Usuario Responsable (Recruiter)</label>
            <select name="usuario" className="form-control" value={proceso.usuario.id} onChange={handleSelectChange} required>
              {usuarios.map((usuario) => (
                <option key={usuario.id} value={usuario.id}>
                  {usuario.nombre} {usuario.apellidos} ({usuario.username})
                </option>
              ))}
            </select>
          </div>

          <div className="col-md-6 mb-3">
            <label className="form-label">Salario</label>
            <input type="number" name="salario" className="form-control" value={proceso.salario} onChange={handleChange} required />
          </div>

          <div className="col-md-12 mb-3">
            <label className="form-label">Tecnologías</label>
            <select 
              name="tecnologias" 
              className="form-control" 
              multiple 
              value={proceso.tecnologias.map(tech => tech.id.toString())}
              onChange={handleTecnologiasChange} 
              required
            >
              {tecnologias.map((tech) => (
                <option key={tech.id} value={tech.id}>{tech.nombre}</option>
              ))}
            </select>
            {/* Chips de tecnologías seleccionadas */}
            <div className="mb-3">
                  <strong>Tecnologías Seleccionadas:</strong>
                  <div className="mt-2">
                    {proceso.tecnologias.map((tech) => (
                      <span key={tech.id} className="badge bg-primary me-2">{tech.nombre}</span>
                    ))}
                  </div>
                </div>
          </div>

          <div className="col-md-12 mb-3">
            <label className="form-label">Descripción</label>
            <textarea name="descripcion" className="form-control" value={proceso.descripcion} onChange={handleChange} required />
          </div>
        </div>

        <button type="submit" className="btn btn-primary" disabled={loading}>
          {loading ? "Actualizando..." : "Actualizar"}
        </button>
      </form>
    </div>
  );
}
