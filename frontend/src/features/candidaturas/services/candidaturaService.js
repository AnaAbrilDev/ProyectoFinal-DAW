import axiosInstance from "../../../utils/axiosConfig"; // Importa la instancia de Axios configurada con la base URL y headers adecuados

// Obtener todas las candidaturas
export const obtenerCandidaturas = async () => {
  try {
    const response = await axiosInstance.get("/candidaturas"); // Solicitud GET para obtener todas las candidaturas
    return response.data;
  } catch (error) {
    console.error("Error al obtener candidaturas:", error);
    throw error; // Relanza el error para manejarlo desde el componente que llame esta función
  }
};

// Obtener una candidatura específica por su ID
export const obtenerCandidaturaPorId = async (id) => {
  try {
    const response = await axiosInstance.get(`/candidaturas/${id}`); // Solicitud GET para una candidatura concreta
    return response.data;
  } catch (error) {
    console.error("Error al obtener candidatura:", error);
    throw error;
  }
};

// Agregar una nueva candidatura
export const agregarCandidatura = async (candidatura) => {
  try {
    const response = await axiosInstance.post("/candidaturas", candidatura); // Solicitud POST para añadir una candidatura
    return response.data;
  } catch (error) {
    console.error("Error al agregar candidatura:", error);
    throw error;
  }
};

// Obtener la lista de perfiles profesionales disponibles
export const obtenerPerfiles = async () => {
  try {
    const response = await axiosInstance.get("/perfiles"); // Solicitud GET para obtener perfiles
    return response.data;
  } catch (error) {
    console.error("Error al obtener perfiles:", error);
    throw error;
  }
};

// Obtener la lista de candidatos disponibles
export const obtenerCandidatos = async () => {
  try {
    const response = await axiosInstance.get("/candidatos");
    return response.data;
  } catch (error) {
    console.error("Error al obtener candidatos:", error);
    throw error;
  }
};

// Obtener los procesos de selección disponibles
export const obtenerProcesos = async () => {
  try {
    const response = await axiosInstance.get("/procesos_seleccion");
    return response.data;
  } catch (error) {
    console.error("Error al obtener procesos_seleccion:", error);
    throw error;
  }
};

// Eliminar candidatura
export const eliminarCandidatura = async (id) => {
  try {
    await axiosInstance.delete(`/candidaturas/${id}`);
  } catch (error) {
    console.error(`Error al eliminar candidatura con ID ${id}:`, error);
    throw error;
  }
};

// Obtener todos los estados de candidatura
export const obtenerEstadosCandidatura = async () => {
  try {
    const response = await axiosInstance.get("/estados_candidatura");
    return response.data;
  } catch (error) {
    console.error("Error al obtener estados de candidatura:", error);
    throw error;
  }
};

// Actualizar el estado de una candidatura
export const actualizarEstadoCandidatura = async (candidaturaId, estadoId, usuarioId) => {
  try {
    await axiosInstance.post(`/historial-estados/candidatura/${candidaturaId}/estado/${estadoId}?usuario_id=${usuarioId}`);
  } catch (error) {
    console.error("Error al actualizar el estado de la candidatura:", error);
    throw error;
  }
};