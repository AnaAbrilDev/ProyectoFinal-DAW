import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // Hook para obtener parámetros de la URL
import { obtenerCandidatoPorId } from "../services/candidatoService"; // Servicio que recupera datos del candidato
import { FaFileAlt, FaUser } from "react-icons/fa"; // Iconos para mejorar la interfaz

/**
 * Componente para visualizar los detalles de un candidato específico.
 * Utiliza el parámetro "id" para obtener la información del candidato desde la API.
 */

export default function VerCandidato() {
  const { id } = useParams(); // Extrae el ID del candidato desde la URL
  const [candidato, setCandidato] = useState(null); // Estado para almacenar los datos del candidato

  useEffect(() => {
    loadCandidato();
  }, []);

  const loadCandidato = async () => {
    try {
      const data = await obtenerCandidatoPorId(id);
      setCandidato(data);
    } catch (error) {
      console.error("Error al obtener candidato:", error);
    }
  };

  if (!candidato) {
    return <div className="container mt-4"><h2>Cargando candidato...</h2></div>;
  }

  // Formato de fecha legible
  const formatearFecha = (fecha) => {
    return new Date(fecha).toLocaleDateString("es-ES", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  return (
    <div className="container mt-4">
      <div className="card shadow-sm border-0">
        <div className="card-body">
          <h3 className="text-primary fw-bold mb-4"><FaUser className="me-2" /> Detalles del Candidato</h3>

          <div className="row">
            {/* Columna Izquierda */}
            <div className="col-md-6">
              <ul className="list-group">
                <li className="list-group-item"><strong>ID:</strong> {candidato.id}</li>
                <li className="list-group-item"><strong>Nombre:</strong> {candidato.nombre} {candidato.apellidos}</li>
                <li className="list-group-item"><strong>Correo:</strong> {candidato.correo}</li>
                <li className="list-group-item"><strong>Teléfono:</strong> {candidato.telefono}</li>
                <li className="list-group-item"><strong>NIF/NIE:</strong> {candidato.nifNie}</li>
                <li className="list-group-item"><strong>Salario Esperado:</strong> {candidato.salario} €</li>
                <li className="list-group-item"><strong>Fecha de Nacimiento:</strong> {formatearFecha(candidato.fechaNacimiento)}</li>
                <li className="list-group-item"><strong>Estado:</strong> {candidato.estado}</li>
                <li className="list-group-item"><strong>Género:</strong> {candidato.genero}</li>
                <li className="list-group-item"><strong>Nivel de Inglés:</strong> {candidato.nivelIngles}</li>
              </ul>
            </div>

            {/* Columna Derecha */}
            <div className="col-md-6">
              <ul className="list-group">
                <li className="list-group-item"><strong>Fuente de Reclutamiento:</strong> {candidato.fuenteReclutamiento}</li>
                <li className="list-group-item"><strong>Perfil Profesional:</strong> {candidato.tipoPerfil?.nombre}</li>
                <li className="list-group-item">
                  <strong>Modalidad Provincia:</strong> 
                  {candidato.modalidadProvincia?.provincia} - {candidato.modalidadProvincia?.modalidad}
                </li>
                
                {/* Sección de CV */}
                <li className="list-group-item">
                  <strong>CV:</strong> 
                  <a href={candidato.cv} target="_blank" rel="noopener noreferrer" className="ms-2 btn btn-outline-primary btn-sm">
                    <FaFileAlt className="me-1" /> Ver CV
                  </a>
                </li>

                {/* Sección de Tecnologías */}
                <li className="list-group-item">
                  <strong>Tecnologías:</strong>
                  <div className="mt-2">
                    {candidato.tecnologias.length > 0 ? (
                      candidato.tecnologias.map((tech, index) => (
                        <span key={index} className="badge bg-primary me-2">{tech.nombre}</span>
                      ))
                    ) : (
                      <span className="text-muted">No especificado</span>
                    )}
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
