import axiosInstance from "../../../utils/axiosConfig"; // Configuración personalizada de Axios para manejar la base URL y headers

//  Obtener todos los candidatos
// Función asíncrona que realiza una solicitud GET para obtener todos los candidatos desde la API
export const obtenerCandidatos = async () => {
  try {
    const response = await axiosInstance.get("/candidatos"); // Ruta de la API para obtener candidatos
    return response.data; // Devuelve los datos obtenidos
  } catch (error) {
    console.error("Error al obtener candidatos:", error);
    throw error; // Lanza el error para que el componente que llama la función pueda manejarlo
  }
};

//  Obtener un candidato por ID
export const obtenerCandidatoPorId = async (id) => {
  try {
    const response = await axiosInstance.get(`/candidatos/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al obtener candidato:", error);
    throw error;
  }
};

//  Crear un candidato
export const agregarCandidato = async (candidato) => {
  try {
    const response = await axiosInstance.post("/candidatos", candidato);
    return response.data;
  } catch (error) {
    console.error("Error al agregar candidato:", error);
    throw error;
  }
};

//  Editar un candidato
export const editarCandidato = async (id, candidato) => {
  try {
    const response = await axiosInstance.put(`/candidatos/${id}`, candidato);
    return response.data;
  } catch (error) {
    console.error("Error al editar candidato:", error);
    throw error;
  }
};

//  Eliminar un candidato
export const eliminarCandidato = async (id) => {
  try {
    await axiosInstance.delete(`/candidatos/${id}`);
  } catch (error) {
    console.error("Error al eliminar candidato:", error);
    throw error;
  }
};

//  Obtener datos para formularios
export const obtenerPerfiles = async () => {
  try {
    const response = await axiosInstance.get("/perfiles");
    return response.data;
  } catch (error) {
    console.error("Error al obtener perfiles:", error);
    throw error;
  }
};

export const obtenerTecnologias = async () => {
  try {
    const response = await axiosInstance.get("/tecnologias");
    return response.data;
  } catch (error) {
    console.error("Error al obtener tecnologías:", error);
    throw error;
  }
};

export const obtenerModalidadesProvincia = async () => {
  try {
    const response = await axiosInstance.get("/modalidades_provincia");
    return response.data;
  } catch (error) {
    console.error("Error al obtener modalidades de provincia:", error);
    throw error;
  }
};
