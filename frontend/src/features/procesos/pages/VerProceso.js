import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { obtenerProcesoPorId } from "../services/procesoService";

/**
 * Componente para mostrar los detalles de un proceso de selección.
 * Obtiene la información del proceso desde el servicio correspondiente
 * y la muestra de forma organizada en tarjetas con categorías diferenciadas.
 */

export default function VerProceso() {
  const { id } = useParams(); // Obtiene el IDe del proceso desde la URL
  const [proceso, setProceso] = useState(null); // Estado para almacenar el proceso

  // Carga los datos del proceso cuando se monta el componente
  useEffect(() => {
    cargarProceso();
  }, []);

  /**
   * Carga los datos del proceso por su ID desde el servicio
   */
  const cargarProceso = async () => {
    try {
      const data = await obtenerProcesoPorId(id);
      setProceso(data); // Almacena el proceso en el estado
    } catch (error) {
      console.error("Error al obtener el proceso:", error);
    }
  };

  // Si aún no se ha cargado el proceso, muestra un mensaje de carga
  if (!proceso) {
    return <div className="container mt-4"><h2>Cargando proceso...</h2></div>;
  }

  /**
   * Formatea una fecha en formato legible para el usuario
   * Por ejemplo, "18 de enero de 2025, 10:30"
   */
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
      <h2 className="text-primary">Detalles del Proceso de Selección</h2>

      <div className="row">
        {/* Tarjeta de Información General */}
        <div className="col-md-6">
          <div className="card shadow-sm mb-4">
            <div className="card-body">
              <h5 className="card-title text-secondary">Información General</h5>
              <ul className="list-group list-group-flush">
                <li className="list-group-item"><strong>ID:</strong> {proceso.id}</li>
                <li className="list-group-item"><strong>Sector del Proyecto:</strong> {proceso.sectorProyecto}</li>
                <li className="list-group-item"><strong>Estado:</strong> <span className={`badge ${proceso.estado === "ACTIVO" ? "bg-success" : "bg-danger"}`}>{proceso.estado}</span></li>
                <li className="list-group-item"><strong>Prioridad:</strong> <span className="badge bg-warning">{proceso.prioridad}</span></li>
                <li className="list-group-item"><strong>Descripción:</strong> {proceso.descripcion}</li>
                <li className="list-group-item"><strong>Salario:</strong> {proceso.salario} €</li>
                <li className="list-group-item"><strong>Fecha de Creación:</strong> {formatearFecha(proceso.fechaCreacion)}</li>
              </ul>
            </div>
          </div>
        </div>

        {/* Información de Modalidad, Usuario y Tecnologías */}
        <div className="col-md-6">
          <div className="card shadow-sm mb-4">
            <div className="card-body">
              <h5 className="card-title text-secondary">Detalles Adicionales</h5>
              <ul className="list-group list-group-flush">
                <li className="list-group-item"><strong>Modalidad Provincia:</strong> {proceso.modalidadProvincia?.provincia} - {proceso.modalidadProvincia?.modalidad}</li>
                <li className="list-group-item"><strong>Perfil Profesional:</strong> {proceso.tipoPerfil?.nombre}</li>
                <li className="list-group-item"><strong>Usuario Responsable:</strong> {proceso.usuario?.nombre} {proceso.usuario?.apellidos} ({proceso.usuario?.username})</li>
                <li className="list-group-item"><strong>Email Usuario:</strong> <a href={`mailto:${proceso.usuario?.email}`} className="text-decoration-none">{proceso.usuario?.email}</a></li>
              </ul>
            </div>
          </div>

          {/* Tecnologías */}
          <div className="card shadow-sm">
            <div className="card-body">
              <h5 className="card-title text-secondary">Tecnologías</h5>
              <div>
                {proceso.tecnologias?.length > 0 ? (
                  proceso.tecnologias.map((tech, index) => (
                    <span key={index} className="badge bg-primary me-2">{tech.nombre}</span>
                  ))
                ) : (
                  <p className="text-muted">No se han asignado tecnologías.</p>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
