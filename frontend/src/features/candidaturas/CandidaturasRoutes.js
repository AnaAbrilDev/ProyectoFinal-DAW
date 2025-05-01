import React from "react";
import { Routes, Route } from "react-router-dom"; // Importación de las rutas para la navegación en la app
import ListadoCandidaturas from "./pages/ListadoCandidaturas"; // Página de listado de candidaturas
import AgregarCandidatura from "./pages/AgregarCandidatura"; // Página para agregar una nueva candidatura
import VerCandidatura from "./pages/VerCandidatura"; // Página para ver una candidatura específica

export default function CandidatosRoutes() {
  return (
    <Routes>
      <Route path="/" element={<ListadoCandidaturas />} />
      <Route path="/agregar" element={<AgregarCandidatura />} />
      <Route path="/ver/:id" element={<VerCandidatura />} />
    </Routes>
  );
}
