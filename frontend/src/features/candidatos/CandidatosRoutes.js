import React from "react"; // Importa la biblioteca React para crear componentes
import { Routes, Route } from "react-router-dom"; // Permite gestionar la navegación entre rutas

// Importación de los componentes asociados a cada ruta
import ListadoCandidatos from "./pages/ListadoCandidatos"; // Lista de candidatos
import AgregarCandidato from "./pages/AgregarCandidato"; // Formulario para agregar un nuevo candidato
import VerCandidato from "./pages/VerCandidato"; // Visualización de los detalles de un candidato específico 
import EditarCandidato from "./pages/EditarCandidato"; // Formulario para editar los datos de un candidato existente

/**
 * Componente que define las rutas para la gestión de candidatos.
 * Utiliza el sistema de enrutamiento de React Router
 */

export default function CandidatosRoutes() {
  return (
    <Routes>
      <Route path="/" element={<ListadoCandidatos />} />
      <Route path="/agregar" element={<AgregarCandidato />} />
      <Route path="/ver/:id" element={<VerCandidato />} />
      <Route path="/editar/:id" element={<EditarCandidato />} />
    </Routes>
  );
}
