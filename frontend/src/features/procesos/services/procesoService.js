import axiosInstance from "../../../utils/axiosConfig";

export const obtenerProcesos = async () => {
  try {
    const response = await axiosInstance.get("/procesos_seleccion");
    return response.data;
  } catch (error) {
    console.error("Error al obtener procesos:", error);
    throw error;
  }
};

export const obtenerProcesoPorId = async (id) => {
  try {
    const response = await axiosInstance.get(`/procesos_seleccion/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error al obtener proceso:", error);
    throw error;
  }
};

export const agregarProceso = async (proceso) => {
  try {
    const response = await axiosInstance.post("/procesos_seleccion", proceso);
    return response.data;
  } catch (error) {
    console.error("Error al agregar proceso:", error);
    throw error;
  }
};

export const editarProceso = async (id, proceso) => {
  try {
    const response = await axiosInstance.put(`/procesos_seleccion/${id}`, proceso);
    return response.data;
  } catch (error) {
    console.error("Error al editar proceso:", error);
    throw error;
  }
};

export const eliminarProceso = async (id) => {
  try {
    await axiosInstance.delete(`/procesos_seleccion/${id}`);
  } catch (error) {
    console.error("Error al eliminar proceso:", error);
    throw error;
  }
};

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

export const obtenerUsuarios = async () => {
  try {
    const response = await axiosInstance.get("/usuarios");
    return response.data;
  } catch (error) {
    console.error("Error al obtener usuarios:", error);
    throw error;
  }
};

