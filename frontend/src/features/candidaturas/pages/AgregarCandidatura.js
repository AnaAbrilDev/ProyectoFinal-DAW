import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";

// Importación de servicios para obtener y gestionar datos de candidaturas
import { 
  agregarCandidatura, 
  obtenerCandidatos, 
  obtenerProcesos,
  obtenerCandidaturas
} from "../services/candidaturaService";

// Estilos y funcionalidad de Select2 para los selectores
import "select2/dist/css/select2.min.css";
import $ from "jquery"; // Importación de jQuery para integrar Select2
import "select2";

// Componente principal para agregar una nueva candidatura
export default function AgregarCandidatura() {
  const navigate = useNavigate();

  // Referencias para los elementos DOM controlados por Select2
  const candidatoRef = useRef(null);
  const procesoRef = useRef(null);

  // Estado que almacena la candidatura al enviar
  const [candidatura, setCandidatura] = useState({
    candidato: "",
    procesoSeleccion: ""
  });

  // Estados para almacenar los datos necesarios
  const [candidatos, setCandidatos] = useState([]);
  const [procesos, setProcesos] = useState([]);
  const [error, setError] = useState("");
  const [roles, setRoles] = useState([]);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const storedRoles = JSON.parse(localStorage.getItem("roles")) || [];
    const storedUserId = localStorage.getItem("userId");

    setRoles(storedRoles);
    setUserId(parseInt(storedUserId));
  }, []);

  useEffect(() => {
    if (roles.length > 0 && userId !== null) {
      cargarDatos();
    }
  }, [roles, userId]);

  useEffect(() => {
    if (candidatos.length > 0) {
      $(candidatoRef.current).select2({
        placeholder: "Seleccione un candidato",
        width: "100%",
        allowClear: true
      }).on("change", function () {
        setCandidatura(prev => ({ ...prev, candidato: { id: parseInt($(this).val()) } }));
      });
    }
  }, [candidatos]);

  useEffect(() => {
    if (procesos.length > 0) {
      $(procesoRef.current).select2({
        placeholder: "Seleccione un proceso",
        width: "100%",
        allowClear: true
      }).on("change", function () {
        setCandidatura(prev => ({ ...prev, procesoSeleccion: { id: parseInt($(this).val()) } }));
      });
    }
  }, [procesos]);

  const cargarDatos = async () => {
    try {
      const candidatosData = await obtenerCandidatos();
      let procesosData = await obtenerProcesos();

      console.log("Roles:", roles);
      console.log("User ID:", userId);

      procesosData = procesosData.filter(proceso => proceso.estado === "ACTIVO");

      if (roles.includes("RECRUITER")) {
        procesosData = procesosData.filter(proceso => proceso.usuario?.id === userId);
      }

      setCandidatos(candidatosData.sort((a, b) => a.nombre.localeCompare(b.nombre)));
      setProcesos(procesosData.sort((a, b) => a.sectorProyecto.localeCompare(b.sectorProyecto)));
    } catch (error) {
      setError("Error al cargar los datos. Por favor, inténtelo nuevamente.");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
  
    // Validar si se han seleccionado candidato y proceso de selección
    if (!candidatura.candidato.id || !candidatura.procesoSeleccion.id) {
      setError("Debes seleccionar un candidato y un proceso.");
      return;
    }
  
    try {
      // Obtener todas las candidaturas existentes
      const candidaturasExistentes = await obtenerCandidaturas();
  
      // Verificar si ya existe una candidatura con el mismo candidato y proceso
      const existeCandidatura = candidaturasExistentes.some(c =>
        c.candidato.id === candidatura.candidato.id &&
        c.procesoSeleccion.id === candidatura.procesoSeleccion.id
      );
  
      if (existeCandidatura) {
        setError("Esta candidatura ya existe. No es posible crearla nuevamente.");
        return;
      }
  
      // Si no existe, proceder a enviarla
      console.log("Enviando datos:", candidatura);
      await agregarCandidatura(candidatura);
      navigate("/candidaturas");
    } catch (error) {
      setError("Error al agregar la candidatura. Verifique los datos e intente nuevamente.");
    }
  };
  

  return (
    <div className="container mt-4">
      <div className="card shadow-sm">
        <div className="card-body">
          <h2 className="text-primary text-center">Agregar Candidatura</h2>

          {error && <div className="alert alert-danger text-center">{error}</div>}

          <form onSubmit={handleSubmit}>
            {/* Select para candidatos con Select2 */}
            <div className="mb-3">
              <label className="form-label">Candidato</label>
              <select ref={candidatoRef} className="form-select">
                <option value="">Seleccione un candidato</option>
                {candidatos.map((candidato) => (
                  <option key={candidato.id} value={candidato.id}>
                    {candidato.nombre} {candidato.apellidos} ({candidato.correo})
                  </option>
                ))}
              </select>
            </div>

            {/* Select para procesos con Select2 */}
            <div className="mb-3">
              <label className="form-label">Proceso de Selección</label>
              <select ref={procesoRef} className="form-select">
                <option value="">Seleccione un proceso</option>
                {procesos.map((proceso) => (
                  <option key={proceso.id} value={proceso.id}>
                    {proceso.sectorProyecto} - {proceso.tipoPerfil.nombre} ({proceso.estado})
                  </option>
                ))}
              </select>
            </div>

            <div className="d-flex justify-content-end">
              <button type="submit" className="btn btn-success">Guardar</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
