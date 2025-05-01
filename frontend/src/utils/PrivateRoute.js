import { Navigate } from "react-router-dom"; // Permite redirigir al usuario a otra ruta
import { isAuthenticated } from "./auth"; // Importamos la función que verifica si el usuario está autenticado

/**
 * Componente que restringe el acceso a rutas privadas
 * Si el usuario está autenticado, se renderiza el componente indicado en element
 * Si no está autenticado, redirige automáticamente a la página de login
 */
const PrivateRoute = ({ element }) => {
  return isAuthenticated() ? element : <Navigate to="/login" />;
};

export default PrivateRoute;