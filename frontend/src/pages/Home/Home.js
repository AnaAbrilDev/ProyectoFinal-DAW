import React from 'react';
import './Home.css'; // Importa el archivo CSS para aplicar estilos específicos
import WebTrackLogo from '../../assets/WebTrackLogo.jpg'; // Ruta al logo de la aplicación

/**
 * Componente Home: Muestra la página principal de bienvenida de la aplicación WebTrack
 * Contiene un mensaje de bienvenida y una breve descripción junto al logo de la aplicación
 */
export default function Home() {
  return (
    <div className="home-container">
      <h1 className="home-title">Bienvenido/a a WebTrack</h1>
      <p className="home-subtitle">
        Esta es tu plataforma de selección de personal. Navega por los procesos de selección, 
        candidatos y gestiona la información con facilidad.
      </p>
      <img src={WebTrackLogo} alt="WebTrack Logo" className="home-logo" />
    </div>
  );
}

