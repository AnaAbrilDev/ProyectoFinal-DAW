import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { obtenerCandidaturaPorId, obtenerEstadosCandidatura, actualizarEstadoCandidatura } from "../services/candidaturaService";

/**
 * Componente para visualizar los detalles de una candidatura específica.
 * Permite ver la información del candidato, proceso de selección, historiaL de estados  y actualizar el estado actual
 */
export default function VerCandidatura() {
  // Obtener el ID de la candidatura de los parámetros de la URL
  const { id } = useParams();
  // Estados locales para manejar datos y la lógica de la vista
  const [candidatura, setCandidatura] = useState(null);
  const [estados, setEstados] = useState([]);
  const [nuevoEstado, setNuevoEstado] = useState("");
  const [mensaje, setMensaje] = useState("");

  // Obtener el ID del usuario autenticado desde el almacenamiento local
  const usuarioId = localStorage.getItem("userId");

  // Cargar datos iniciales al montar el componente
  useEffect(() => {
    cargarCandidatura(); 
    cargarEstados();
  }, []);

  // Obtener los detalles de la candidatura por ID
  const cargarCandidatura = async () => {
    try {
      const data = await obtenerCandidaturaPorId(id);
      setCandidatura(data);
    } catch (error) {
      console.error("Error al obtener la candidatura:", error);
    }
  };

  // Obtener los estados disponibles para la candidatura
  const cargarEstados = async () => {
    try {
      const data = await obtenerEstadosCandidatura();
      setEstados(data);
    } catch (error) {
      console.error("Error al obtener los estados:", error);
    }
  };

  // Actualizar el estado seleccionado cuando el usuario elija un nuevo estado
  const handleEstadoChange = (e) => {
    setNuevoEstado(e.target.value);
  };

  // Manejar la actualización del estado de la candidatura
  const handleActualizarEstado = async () => {
    if (!nuevoEstado) {
      setMensaje("Por favor, selecciona un estado.");
      return;
    }

    try {
      await actualizarEstadoCandidatura(id, nuevoEstado, usuarioId);
      setMensaje("Estado actualizado correctamente.");
      cargarCandidatura(); // Volver a cargar la candidatura con el estado actualizado
    } catch (error) {
      setMensaje("Error al actualizar el estado.");
      console.error("Error al actualizar el estado de la candidatura:", error);
    }
  };

  // Mostrar un mensaje de carga si los datos aún no están disponibles
  if (!candidatura) {
    return <div className="container mt-4"><h2>Cargando candidatura...</h2></div>;
  }

  const formatearFecha = (fecha) => {
    return new Date(fecha).toLocaleDateString("es-ES", {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm">
        <div className="card-body">
          <h2 className="text-primary">Detalles de la Candidatura</h2>

          {/* Datos del Candidato */}
          <div className="card mt-3">
            <div className="card-header bg-light"><strong>Datos del Candidato</strong></div>
            <ul className="list-group list-group-flush">
              <li className="list-group-item"><strong>Nombre:</strong> {candidatura.candidato.nombre} {candidatura.candidato.apellidos}</li>
              <li className="list-group-item"><strong>Correo:</strong> {candidatura.candidato.correo}</li>
              <li className="list-group-item"><strong>Teléfono:</strong> {candidatura.candidato.telefono}</li>
              <li className="list-group-item"><strong>NIF/NIE:</strong> {candidatura.candidato.nifNie}</li>
              <li className="list-group-item"><strong>Salario Esperado:</strong> {candidatura.candidato.salario} €</li>
              <li className="list-group-item"><strong>CV:</strong> <a href={candidatura.candidato.cv} target="_blank" rel="noopener noreferrer">Ver CV</a></li>
              <li className="list-group-item"><strong>Estado:</strong> {candidatura.candidato.estado}</li>
              <li className="list-group-item"><strong>Perfil Profesional:</strong> {candidatura.candidato.tipoPerfil.nombre}</li>
            </ul>
          </div>

          {/* Datos del Proceso de Selección */}
          <div className="card mt-3">
            <div className="card-header bg-light"><strong>Datos del Proceso de Selección</strong></div>
            <ul className="list-group list-group-flush">
              <li className="list-group-item"><strong>Sector del Proyecto:</strong> {candidatura.procesoSeleccion.sectorProyecto}</li>
              <li className="list-group-item"><strong>Estado:</strong> {candidatura.procesoSeleccion.estado}</li>
              <li className="list-group-item"><strong>Perfil Profesional:</strong> {candidatura.procesoSeleccion.tipoPerfil.nombre}</li>
              <li className="list-group-item"><strong>Usuario Responsable:</strong> {candidatura.procesoSeleccion.usuario.nombre} {candidatura.procesoSeleccion.usuario.apellidos}</li>
            </ul>
          </div>

          {/* Historial de Estados */}
          <div className="card mt-3">
            <div className="card-header bg-light"><strong>Historial de Estados</strong></div>
            {candidatura.historialEstados.length > 0 ? (
              <ul className="list-group list-group-flush">
                {candidatura.historialEstados
                  .sort((a, b) => new Date(b.fechaCambio) - new Date(a.fechaCambio))
                  .map((estado, index) => (
                    <li key={index} className="list-group-item">
                      <strong>{estado.estado}</strong> - {formatearFecha(estado.fechaCambio)} - <i>Usuario: {estado.usuario?.username ?? 'Sistema'}</i>
                    </li>
                  ))}
              </ul>
            ) : (
              <div className="list-group-item">No hay historial de cambios.</div>
            )}
          </div>

          {/* Actualizar Estado */}
          <div className="card mt-3">
            <div className="card-header bg-light"><strong>Actualizar Estado</strong></div>
            <div className="list-group-item">
              <select className="form-select" value={nuevoEstado} onChange={handleEstadoChange}>
                <option value="">Seleccione un nuevo estado</option>
                {estados.map((estado) => (
                  <option key={estado.id} value={estado.id}>{estado.nombre}</option>
                ))}
              </select>
            </div>
            <div className="list-group-item">
              <button className="btn btn-primary" onClick={handleActualizarEstado}>Actualizar Estado</button>
            </div>
          </div>

          {mensaje && <div className={`alert ${mensaje.includes("Error") ? "alert-danger" : "alert-success"} mt-3`}>{mensaje}</div>}
        </div>
      </div>
    </div>
  );
}
