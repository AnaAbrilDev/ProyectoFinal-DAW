import axios from "axios";

// Crear una instancia de axios
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api", // Cambia según tu backend
});

// Añadir un interceptor para incluir el token en cada solicitud
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Añadir un interceptor de respuesta para manejar errores globales
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      console.error("Error en la respuesta de la API:", error.response);

      if (error.response.status === 401) {
        //localStorage.removeItem("token"); // Eliminar el token si es inválido
      }
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
