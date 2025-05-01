import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { 
  obtenerCandidatoPorId, 
  editarCandidato, 
  obtenerPerfiles, 
  obtenerTecnologias, 
  obtenerModalidadesProvincia 
} from "../services/candidatoService"; // Importación de servicios para la lógica del CRUD
import { FaSave } from "react-icons/fa"; // Icono para el botón de guardar

/**
 * Componente funcional que permite editar la información de un candidato existente.
 * Utiliza formularios controlados con hooks para gestionar el estado del componente
 */

export default function EditarCandidato() {
  const { id } = useParams(); // Obtiene el ID del candidato desde la URL
  const navigate = useNavigate(); // Hook para redirigir tras la edición
  const [error, setError] = useState("");

  // Estado del candidato con valores por defecto
  const [candidato, setCandidato] = useState({
    nombre: "",
    apellidos: "",
    correo: "",
    telefono: "",
    nifNie: "",
    salario: "",
    cv: "",
    fechaNacimiento: "",
    estado: "DISPONIBLE",
    genero: "MASCULINO",
    nivelIngles: "MEDIO",
    fuenteReclutamiento: "LINKEDIN",
    tipoPerfil: { id: "" },
    modalidadProvincia: { id: "" },
    tecnologias: [],
  });

  // Listas para opciones desplegables
  const [perfiles, setPerfiles] = useState([]);
  const [tecnologias, setTecnologias] = useState([]);
  const [modalidadesProvincia, setModalidadesProvincia] = useState([]);
  
  // Opciones predefinidas para ciertos campos
  const estados = ["DISPONIBLE", "EN_PROCESO", "CONTRATADO", "NO_DISPONIBLE"];
  const generos = ["MASCULINO", "FEMENINO", "OTRO"];
  const nivelesIngles = ["BAJO", "MEDIO", "ALTO"];
  const fuentesReclutamiento = ["INFOJOBS", "LINKEDIN", "RECOMENDADO", "OTROS"];

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const candidatoData = await obtenerCandidatoPorId(id);
      
      setCandidato({
        ...candidatoData,
        estado: candidatoData.estado.toUpperCase(), // Normaliza a mayúsculas
        genero: candidatoData.genero.toUpperCase(),
        nivelIngles: candidatoData.nivelIngles.toUpperCase(),
        fuenteReclutamiento: candidatoData.fuenteReclutamiento.toUpperCase(),
        tipoPerfil: { id: candidatoData.tipoPerfil?.id || "" },
        modalidadProvincia: { id: candidatoData.modalidadProvincia?.id || "" },
        tecnologias: candidatoData.tecnologias.map((tech) => ({
            id: tech.id,
            nombre: tech.nombre,
        })),
    });

      setPerfiles(await obtenerPerfiles());
      setTecnologias(await obtenerTecnologias());
      setModalidadesProvincia(await obtenerModalidadesProvincia());
    } catch (error) {
      setError("Error al cargar los datos.");
    }
  };

  const handleChange = (e) => {
    setCandidato({
      ...candidato,
      [e.target.name]: e.target.value,
    });
  };

  const handleSelectChange = (e) => {
    setCandidato({
      ...candidato,
      [e.target.name]: { id: parseInt(e.target.value) },
    });
  };

  const handleSelectSimpleChange = (e) => {
    setCandidato({
      ...candidato,
      [e.target.name]: e.target.value,
    });
  };

  const handleTecnologiasChange = (e) => {
    const selectedTecnologias = Array.from(e.target.selectedOptions).map(option => ({
      id: parseInt(option.value),
      nombre: option.text
    }));
    setCandidato({
      ...candidato,
      tecnologias: selectedTecnologias,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const candidatoParaEnviar = {
        ...candidato,
        tecnologias: candidato.tecnologias.map(tech => ({ id: tech.id })), // Solo IDs
      };
  
      console.log("Enviando datos actualizados:", candidatoParaEnviar);
      await editarCandidato(id, candidatoParaEnviar);
      navigate("/candidatos");
    } catch (error) {
      console.log(error.response.data);
      setError("Error al actualizar candidato: " + error.response.data);
    }
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm border-0">
        <div className="card-body">
          <h3 className="text-primary fw-bold mb-4">Editar Candidato</h3>

          {error && <div className="alert alert-danger">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="row">
              {/* Columna Izquierda */}
              <div className="col-md-6">
                <div className="mb-3">
                  <label className="form-label">Nombre</label>
                  <input type="text" name="nombre" className="form-control" value={candidato.nombre} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Apellidos</label>
                  <input type="text" name="apellidos" className="form-control" value={candidato.apellidos} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Correo</label>
                  <input type="email" name="correo" className="form-control" value={candidato.correo} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Teléfono</label>
                  <input type="text" name="telefono" className="form-control" value={candidato.telefono} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">NIF/NIE</label>
                  <input type="text" name="nifNie" className="form-control" value={candidato.nifNie} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Salario (€)</label>
                  <input type="number" name="salario" className="form-control" value={candidato.salario} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Fecha de Nacimiento</label>
                  <input type="date" name="fechaNacimiento" className="form-control" value={candidato.fechaNacimiento} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Estado</label>
                  <select name="estado" className="form-control" onChange={handleSelectSimpleChange} required>
                    {estados.map((estado) => (
                      <option key={estado} value={estado}>{estado}</option>
                    ))}
                  </select>
                </div>
              </div>

              {/* Columna Derecha */}
              <div className="col-md-6">
                <div className="mb-3">
                  <label className="form-label">Género</label>
                  <select name="genero" className="form-control" onChange={handleSelectSimpleChange} required>
                    {generos.map((genero) => (
                      <option key={genero} value={genero}>{genero}</option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Nivel de Inglés</label>
                  <select name="nivelIngles" className="form-control" onChange={handleSelectSimpleChange} required>
                    {nivelesIngles.map((nivel) => (
                      <option key={nivel} value={nivel}>{nivel}</option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Fuente de Reclutamiento</label>
                  <select name="fuenteReclutamiento" className="form-control" onChange={handleSelectSimpleChange} required>
                    {fuentesReclutamiento.map((fuente) => (
                      <option key={fuente} value={fuente}>{fuente}</option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">URL CV</label>
                  <input type="text" name="cv" className="form-control" value={candidato.cv} onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Modalidad Provincia</label>
                  <select name="modalidadProvincia" className="form-control" value={candidato.modalidadProvincia.id} onChange={handleSelectChange} required>
                    {modalidadesProvincia.map((modalidad) => (
                      <option key={modalidad.id} value={modalidad.id}>
                        {modalidad.provincia} - {modalidad.modalidad}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Perfil Profesional</label>
                  <select name="tipoPerfil" className="form-control" value={candidato.tipoPerfil.id} onChange={handleSelectChange} required>
                    {perfiles.map((perfil) => (
                      <option key={perfil.id} value={perfil.id}>{perfil.nombre}</option>
                    ))}
                  </select>
                </div>

                {/* Tecnologías - Selector con etiquetas */}
                <div className="mb-3">
                  <label className="form-label">Tecnologías</label>
                  <select 
                    name="tecnologias" 
                    className="form-control" 
                    multiple 
                    value={candidato.tecnologias.map(tech => tech.id.toString())}
                    onChange={handleTecnologiasChange} 
                    required
                  >
                    {tecnologias.map((tech) => (
                      <option key={tech.id} value={tech.id}>{tech.nombre}</option>
                    ))}
                  </select>
                </div>

                {/* Chips de tecnologías seleccionadas */}
                <div className="mb-3">
                  <strong>Tecnologías Seleccionadas:</strong>
                  <div className="mt-2">
                    {candidato.tecnologias.map((tech) => (
                      <span key={tech.id} className="badge bg-primary me-2">{tech.nombre}</span>
                    ))}
                  </div>
                </div>
              </div>
            </div>

            <div className="text-end">
              <button type="submit" className="btn btn-primary">
                <FaSave className="me-2" /> Actualizar Candidato
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
