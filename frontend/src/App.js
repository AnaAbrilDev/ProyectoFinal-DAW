// Estilos globales de la aplicación
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";

// Importaciones para el enrutamiento
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from "react-router-dom";

// Componente de navegación y páginas principales
import Navbar from "./layout/Navbar";
import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";

// Rutas organizadas por módulos
import UsuariosRoutes from "./features/usuarios/UsuariosRoutes";
import CandidatosRoutes from "./features/candidatos/CandidatosRoutes";
import ProcesosRoutes from "./features/procesos/ProcesosRoutes";
import CandidaturasRoutes from "./features/candidaturas/CandidaturasRoutes";

// Componente principal que gestiona el contenido de la aplicación
const AppContent = () => {
  const location = useLocation(); // Permite acceder a la ruta actual


  return (
    <div className="App">
      {/* Mostrar la barra de navegación solo si no estamos en la página de login */}
      {location.pathname !== "/login" && <Navbar />}
      <div className="container mt-4">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Navigate to="/home" />} />
          <Route path="/home" element={localStorage.getItem("token") ? <Home /> : <Navigate to="/login" />} />
          <Route path="/usuarios/*" element={localStorage.getItem("token") ? <UsuariosRoutes /> : <Navigate to="/login" />} />
          <Route path="/candidatos/*" element={localStorage.getItem("token") ? <CandidatosRoutes /> : <Navigate to="/login" />} />
          <Route path="/procesos/*" element={localStorage.getItem("token") ? <ProcesosRoutes /> : <Navigate to="/login" />} />
          <Route path="/candidaturas/*" element={localStorage.getItem("token") ? <CandidaturasRoutes /> : <Navigate to="/login" />} />
        </Routes>
      </div>
    </div>
  );
};

// Componente principal que envuelve toda la aplicación en el Router
function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

export default App;
