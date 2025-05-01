# WebTrack - Sistema de Selección de Personal (ATS)

**Autora:** Ana Abril Requena  
**Proyecto Final del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Web (DAW)**

## Descripción

**WebTrack** es una aplicación web desarrollada como sistema de seguimiento de candidatos (ATS, por sus siglas en inglés) orientada al sector IT. Su objetivo es optimizar y facilitar los procesos de selección de personal mediante funcionalidades personalizadas adaptadas a los flujos reales de trabajo en departamentos de recursos humanos.

La plataforma permite la gestión completa de procesos de selección: desde la creación de vacantes y candidatos hasta el seguimiento detallado de las candidaturas por cada etapa del proceso.

## Objetivos del Proyecto

- Desarrollar un sistema web funcional y seguro que permita la gestión eficiente de procesos de selección.
- Facilitar el registro, búsqueda y asociación de candidatos a procesos de selección.
- Permitir un seguimiento visual del estado de cada candidatura.
- Proporcionar roles diferenciados para coordinadores y técnicos de selección.
- Optimizar la experiencia de usuario con una interfaz clara y responsiva.

## Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot** (REST API, MVC)
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **Maven**

### Frontend
- **React.js**
- **HTML5 / CSS3 / JavaScript**
- **Bootstrap**
- **Axios**

### Herramientas y Otros
- **Postman** (pruebas API)
- **Eclipse** (backend)
- **Visual Studio Code** (frontend)
- **Git & GitHub**
- **Trello** (Kanban)
- **Miro / Draw.io** (wireframes y diagramas)

## Instalación y Uso Local

### Requisitos Previos
- JDK 17+
- Node.js
- MySQL

### Configuración

1. **Base de Datos**
   - Crear una base de datos llamada `webtrack_db`
   - Permitir que Spring Boot la cree automáticamente

2. **Backend**
   ```bash
   cd backend
   mvn spring-boot:run
   
3. **Frontend**
   cd frontend
   npm install
   npm start

   Accede a la app desde: http://localhost:3000
   El backend estará disponible en: http://localhost:8080

   **Funcionalidades Clave**
- Sistema de autenticación y autorización con roles (JWT)

- CRUD completo de usuarios, candidatos y procesos de selección

- Asociación de candidatos a procesos

- Gestión de estados y seguimiento de candidaturas

- Búsqueda y filtrado por múltiples criterios

- Historial de cambios 

- Diseño responsivo y experiencia de usuario mejorada
