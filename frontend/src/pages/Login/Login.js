import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../utils/axiosConfig"; // Importar Axios
import WebTrackLogo from "../../assets/WebTrackLogo.jpg"; // Importar logo
import "./Login.css"; // Importar estilos

const Login = () => {
  // Estados para gestionar datos del formulario y errores
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Maneja el envío del formulario de login
  const handleSubmit = async (e) => { 
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const response = await axiosInstance.post("/auth/login", { username, password });
      
      console.log(response); // Confirmación para depuración
      
      // Guardar token y roles en localStorage para gestionar sesión
      localStorage.setItem("token", response.data.accessToken);
      localStorage.setItem("roles", JSON.stringify(response.data.roles));
      localStorage.setItem("userId", response.data.userId); // Guardar el ID del usuario

      console.log("Inicio de sesión exitoso. Roles:", response.data.roles);
      navigate("/home"); // Redirigir a Home después del login
    } catch (err) {
      setError("Usuario o contraseña incorrectos");
    } finally {
      setLoading(false); // Desactivar el estado de carga
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <img src={WebTrackLogo} alt="WebTrack Logo" className="login-logo" />
        <h1>WebTrack</h1>
        <p>Software de Selección de Personal</p>
        <form onSubmit={handleSubmit}>
          {error && <p className="error-message text-danger">{error}</p>}
         
         {/* Campo de Usuario */}
          <div className="form-group">
            <input
              type="text"
              placeholder="Usuario"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              className="form-control"
            />
          </div>

          {/* Campo de Contraseña */}
          <div className="form-group">
            <input
              type="password"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="form-control"
            />
          </div>
          <button type="submit" className="btn btn-primary w-100" disabled={loading}>
            {loading ? "Cargando..." : "Entrar"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
