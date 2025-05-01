const isAuthenticated = () => {
    const token = localStorage.getItem('token');
    return !!token; // Devuelve true si hay token, false si no
  };
  