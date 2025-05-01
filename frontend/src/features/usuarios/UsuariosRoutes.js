import React from "react";
import { Routes, Route } from "react-router-dom";
import ListadoUsuarios from "./pages/ListadoUsuarios";
import AgregarUsuario from "./pages/AgregarUsuario";
import EditarUsuario from "./pages/EditarUsuario";
import VerUsuario from "./pages/VerUsuario";
import PerfilUsuario from "./pages/PerfilUsuario";

/**
 * componente que gestiona las rutas relacionadas con la gestión de usuarios.
 * Permite la navegación entre las vistas para listar, agregar, editar y ver el perfil de los usuarios.
 */

export default function UsuariosRoutes() {
  return (
    <Routes>
      <Route path="/" element={<ListadoUsuarios />} />
      <Route path="/agregar" element={<AgregarUsuario />} />
      <Route path="/editar/:id" element={<EditarUsuario />} />
      <Route path="/ver/:id" element={<VerUsuario />} />
      <Route path="/perfil/:id" element={<PerfilUsuario />} />
    </Routes>
  );
}
