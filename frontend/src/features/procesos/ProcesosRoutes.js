import React from "react";
import { Routes, Route } from "react-router-dom";
import ListadoProcesos from "./pages/ListadoProcesos";
import AgregarProceso from "./pages/AgregarProceso";
import VerProceso from "./pages/VerProceso";
import EditarProceso from "./pages/EditarProceso";

/**
 * Componente encargado de definir y gestionar las rutas relacionadas con los procesos de selección.
 * Permite la navegación entre diferentes vistas de procesos, como la lista, agregar, ver y editar.
 */

export default function CandidatosRoutes() {
  return (
    <Routes>
      <Route path="/" element={<ListadoProcesos />} />
      <Route path="/agregar" element={<AgregarProceso />} />
      <Route path="/editar/:id" element={<EditarProceso />} />
      <Route path="/ver/:id" element={<VerProceso />} />
    </Routes>
  );
}
