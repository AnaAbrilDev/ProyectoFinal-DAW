import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { 
  agregarCandidato, 
  obtenerPerfiles, 
  obtenerTecnologias, 
  obtenerModalidadesProvincia 
} from "../services/candidatoService";
import { FaSave } from "react-icons/fa";

/**
 * Componente AgregarCandidato
 * Permite agregar un nuevo candidato al sistema mediante un formulario completo
 * 
 * - Utiliza hooks como useState para manejar el estado del formulario.
 * - Realiza peticiones asíncronas para obtener datos de perfiles profesionales, tecnologías y modalidades de trabajo
 * - Incluye validaciones y manejo de errores
 */

export default function AgregarCandidato() {
  const navigate = useNavigate();

  // Estado inicial del candidato
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
    tipoPerfil: "",
    modalidadProvincia: "",
    tecnologias: [],
  });

  // Estados para almacenar datos de opciones
  const [perfiles, setPerfiles] = useState([]);
  const [tecnologias, setTecnologias] = useState([]);
  const [modalidadesProvincia, setModalidadesProvincia] = useState([]);
  const [error, setError] = useState("");

  // Opciones predefinidas
  const estados = ["DISPONIBLE", "EN PROCESO", "CONTRATADO", "NO DISPONIBLE"];
  const generos = ["MASCULINO", "FEMENINO", "OTRO"];
  const nivelesIngles = ["BAJO", "MEDIO", "ALTO"];
  const fuentesReclutamiento = ["INFOJOBS", "LINKEDIN", "RECOMENDADO", "OTROS"];

  // Cargar datos iniciales al montar el componente
  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      setPerfiles(await obtenerPerfiles());
      setTecnologias(await obtenerTecnologias());
      setModalidadesProvincia(await obtenerModalidadesProvincia());
    } catch (error) {
      setError("Error al cargar datos.");
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
      console.log(candidatoParaEnviar);
      await agregarCandidato(candidatoParaEnviar);
      navigate("/candidatos");
    } catch (error) {
      console.log(error.response.data);
      setError("Error al agregar candidato: " + error.response.data);
    }
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm border-0">
        <div className="card-body">
          <h3 className="text-primary fw-bold mb-4 text-center">Agregar Candidato</h3>

          {error && <div className="alert alert-danger">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="row">
              {/* Columna Izquierda */}
              <div className="col-md-6">
                <div className="mb-3">
                  <label className="form-label">Nombre</label>
                  <input type="text" name="nombre" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Apellidos</label>
                  <input type="text" name="apellidos" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Correo</label>
                  <input type="email" name="correo" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Teléfono</label>
                  <input type="text" name="telefono" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">NIF/NIE</label>
                  <input type="text" name="nifNie" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Salario (€)</label>
                  <input type="number" name="salario" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Estado</label>
                  <select name="estado" className="form-control" onChange={handleSelectSimpleChange} required>
                    {estados.map((estado) => (
                      <option key={estado} value={estado}>{estado}</option>
                    ))}
                  </select>
                </div>
                <div className="mb-3">
                  <label className="form-label">Género</label>
                  <select name="genero" className="form-control" onChange={handleSelectSimpleChange} required>
                    {generos.map((genero) => (
                      <option key={genero} value={genero}>{genero}</option>
                    ))}
                  </select>
                </div>
              </div>

              {/* Columna Derecha */}
              <div className="col-md-6"><div className="mb-3">
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
                  <input type="text" name="cv" className="form-control" onChange={handleChange} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">Fecha de Nacimiento</label>
                  <input type="date" name="fechaNacimiento" className="form-control" onChange={handleChange} required />
                </div>

                <div className="mb-3">
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

                <div className="mb-3">
                  <label className="form-label">Perfil Profesional</label>
                  <select name="tipoPerfil" className="form-control" onChange={handleSelectChange} required>
                    <option value="">Seleccione un perfil</option>
                    {perfiles.map((perfil) => (
                      <option key={perfil.id} value={perfil.id}>{perfil.nombre}</option>
                    ))}
                  </select>
                </div>

                <div className="mb-3">
                  <label className="form-label">Tecnologías</label>
                  <select name="tecnologias" className="form-control" multiple onChange={handleTecnologiasChange} required>
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

            <div className="text-end mt-4">
              <button type="submit" className="btn btn-success btn-lg">
                <FaSave className="me-2" /> Guardar Candidato
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
