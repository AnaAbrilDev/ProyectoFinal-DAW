import axiosInstance from '../../../utils/axiosConfig';

// Obtener todos los usuarios
export const obtenerUsuarios = async () => {
  try {
    const response = await axiosInstance.get('/usuarios');
    return response.data;
  } catch (error) {
    console.error('Error al obtener usuarios:', error);
    throw error;
  }
};

// Obtener un usuario por ID
export const obtenerUsuarioPorId = async (id) => {
  try {
    const response = await axiosInstance.get(`/usuarios/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error al obtener el usuario con ID ${id}:`, error);
    throw error;
  }
};

// Agregar un usuario
export const agregarUsuario = async (usuario) => {
  try {
    const response = await axiosInstance.post('/usuarios', usuario);
    return response.data;
  } catch (error) {
    console.error('Error al agregar usuario:', error);
    throw error;
  }
};

// Editar usuario
export const editarUsuario = async (id, usuario) => {
  try {
    const response = await axiosInstance.put(`/usuarios/${id}`, usuario);
    return response.data;
  } catch (error) {
    console.error(`Error al editar usuario con ID ${id}:`, error);
    throw error;
  }
};

// Eliminar usuario
export const eliminarUsuario = async (id) => {
  try {
    await axiosInstance.delete(`/usuarios/${id}`);
  } catch (error) {
    console.error(`Error al eliminar usuario con ID ${id}:`, error);
    throw error;
  }
};
