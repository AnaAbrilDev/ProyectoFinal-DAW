package com.webtrack.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.webtrack.dto.ModalidadProvinciaDTO;
import com.webtrack.dto.ProcesoSeleccionDTO;
import com.webtrack.model.ModalidadProvincia;
import com.webtrack.model.ProcesoSeleccion;
import com.webtrack.model.Tecnologia;
import com.webtrack.model.TipoPerfil;
import com.webtrack.model.Usuario;
import com.webtrack.repository.ModalidadProvinciaRepositorio;
import com.webtrack.repository.ProcesoSeleccionRepositorio;
import com.webtrack.repository.TecnologiaRepositorio;
import com.webtrack.repository.TipoPerfilRepositorio;
import com.webtrack.repository.UsuarioRepositorio;

/**
 * Servicio para la gestión de procesos de selección.
 * Permite realizar operaciones CRUD y validar relaciones entre entidades asociadas.
 */

@Service
public class ProcesoSeleccionService {
	
	// Repositorios para gestionar las entidades relacionadas
    private final ProcesoSeleccionRepositorio procesoSeleccionRepositorio;
    private final ModalidadProvinciaRepositorio modalidadProvinciaRepositorio;
    private final TipoPerfilRepositorio tipoPerfilRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TecnologiaRepositorio tecnologiaRepositorio;

    // Constructor que inyecta las dependencias necesarias
    public ProcesoSeleccionService(ProcesoSeleccionRepositorio procesoSeleccionRepositorio,
                                    ModalidadProvinciaRepositorio modalidadProvinciaRepositorio,
                                    TipoPerfilRepositorio tipoPerfilRepositorio,
                                    UsuarioRepositorio usuarioRepositorio,
                                    TecnologiaRepositorio tecnologiaRepositorio) {
        this.procesoSeleccionRepositorio = procesoSeleccionRepositorio;
        this.modalidadProvinciaRepositorio = modalidadProvinciaRepositorio;
        this.tipoPerfilRepositorio = tipoPerfilRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.tecnologiaRepositorio = tecnologiaRepositorio;
    }

    /**
     * Obtiene una lista de todas las modalidades y provincias en formato DTO
     * @return Lista de ModalidadProvinciaDTO
     */
    public List<ModalidadProvinciaDTO> obtenerModalidadesProvincia() {
        return modalidadProvinciaRepositorio.findAll().stream()
                .map(ModalidadProvinciaDTO::new) 
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los procesos de selección como DTOs
     * @return Lista de ProcesoSeleccionDTO
     */
    public List<ProcesoSeleccionDTO> obtenerTodos() {
        return procesoSeleccionRepositorio.findAll().stream()
                .map(ProcesoSeleccionDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un proceso de selección por su ID
     * @param id ID del proceso de selección
     * @return DTO del proceso de selección encontrado
     * @throws RuntimeExcepcion si el proceso no se encuentra
     */
    public ProcesoSeleccionDTO obtenerPorId(Long id) {
        ProcesoSeleccion proceso = procesoSeleccionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Proceso de selección no encontrado con ID: " + id));
        return new ProcesoSeleccionDTO(proceso);
    }

    /**
     * Crea un nuevo proceso de selección
     * @param procesoSeleccion Entidad del proceso de selección a crear
     * @return ProcesoSeleccion creado
     */
    public ProcesoSeleccion crearProceso(ProcesoSeleccion procesoSeleccion) {
        validarRelaciones(procesoSeleccion);
        procesoSeleccion.setFechaCreacion(java.time.LocalDateTime.now());
        return procesoSeleccionRepositorio.save(procesoSeleccion);
    }

    public ProcesoSeleccion actualizarProceso(Long id, ProcesoSeleccion updatedProceso) {
        return procesoSeleccionRepositorio.findById(id).map(proceso -> {
            proceso.setSectorProyecto(updatedProceso.getSectorProyecto());
            proceso.setEstado(updatedProceso.getEstado());
            proceso.setPrioridad(updatedProceso.getPrioridad());
            proceso.setModalidadProvincia(updatedProceso.getModalidadProvincia());
            proceso.setTipoPerfil(updatedProceso.getTipoPerfil());
            proceso.setUsuario(updatedProceso.getUsuario());
            proceso.setDescripcion(updatedProceso.getDescripcion());
            proceso.setSalario(updatedProceso.getSalario());

            // Validar y actualizar tecnologías
            if (updatedProceso.getTecnologias() != null) {
                Set<Tecnologia> tecnologias = updatedProceso.getTecnologias().stream()
                        .map(tecnologia -> tecnologiaRepositorio.findById(tecnologia.getId())
                                .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada con ID: " + tecnologia.getId())))
                        .collect(Collectors.toSet());
                proceso.setTecnologias(tecnologias);
            }

            validarRelaciones(proceso);
            return procesoSeleccionRepositorio.save(proceso);
        }).orElseThrow(() -> new RuntimeException("Proceso de selección no encontrado con ID: " + id));
    }


    public void eliminarProceso(Long id) {
        procesoSeleccionRepositorio.deleteById(id);
    }

    private void validarRelaciones(ProcesoSeleccion procesoSeleccion) {
        // Validar ModalidadProvincia
        if (procesoSeleccion.getModalidadProvincia() != null) {
            Long modalidadProvinciaId = procesoSeleccion.getModalidadProvincia().getId();
            ModalidadProvincia modalidadProvincia = modalidadProvinciaRepositorio.findById(modalidadProvinciaId)
                    .orElseThrow(() -> new RuntimeException("ModalidadProvincia no encontrada con ID: " + modalidadProvinciaId));
            procesoSeleccion.setModalidadProvincia(modalidadProvincia);
        }

        // Validar TipoPerfil
        if (procesoSeleccion.getTipoPerfil() != null) {
            Long tipoPerfilId = procesoSeleccion.getTipoPerfil().getId();
            TipoPerfil tipoPerfil = tipoPerfilRepositorio.findById(tipoPerfilId)
                    .orElseThrow(() -> new RuntimeException("TipoPerfil no encontrado con ID: " + tipoPerfilId));
            procesoSeleccion.setTipoPerfil(tipoPerfil);
        }

        // Validar Usuario
        if (procesoSeleccion.getUsuario() != null) {
            Long usuarioId = procesoSeleccion.getUsuario().getId();
            Usuario usuario = usuarioRepositorio.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));
            procesoSeleccion.setUsuario(usuario);
        }

        // Validar Tecnologias
        if (procesoSeleccion.getTecnologias() != null && !procesoSeleccion.getTecnologias().isEmpty()) {
            Set<Tecnologia> tecnologias = procesoSeleccion.getTecnologias().stream()
                    .map(tecnologia -> tecnologiaRepositorio.findById(tecnologia.getId())
                            .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada con ID: " + tecnologia.getId())))
                    .collect(Collectors.toSet());
            procesoSeleccion.setTecnologias(tecnologias);
        }
    }
}
