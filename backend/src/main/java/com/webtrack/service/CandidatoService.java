package com.webtrack.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.webtrack.dto.CandidatoDTO;
import com.webtrack.model.Candidato;
import com.webtrack.model.ModalidadProvincia;
import com.webtrack.model.Tecnologia;
import com.webtrack.model.TipoPerfil;
import com.webtrack.repository.CandidatoRepositorio;
import com.webtrack.repository.ModalidadProvinciaRepositorio;
import com.webtrack.repository.TecnologiaRepositorio;
import com.webtrack.repository.TipoPerfilRepositorio;

/**
 * Servicio para gestionar operaciones relacionadas con Candidatos.
 * Incluye validación de NIF y asociación con entidades relacionadas (TipoPerfil, Tecnologías, etc.)
 */

@Service
public class CandidatoService {

    private final CandidatoRepositorio candidatoRepositorio;
    private final TipoPerfilRepositorio tipoPerfilRepositorio;
    private final ModalidadProvinciaRepositorio modalidadProvinciaRepositorio;
    private final TecnologiaRepositorio tecnologiaRepositorio;

    public CandidatoService(CandidatoRepositorio candidatoRepositorio,
                            TipoPerfilRepositorio tipoPerfilRepositorio,
                            ModalidadProvinciaRepositorio modalidadProvinciaRepositorio,
                            TecnologiaRepositorio tecnologiaRepositorio) {
        this.candidatoRepositorio = candidatoRepositorio;
        this.tipoPerfilRepositorio = tipoPerfilRepositorio;
        this.modalidadProvinciaRepositorio = modalidadProvinciaRepositorio;
        this.tecnologiaRepositorio = tecnologiaRepositorio;
    }

    public List<CandidatoDTO> obtenerTodos() {
        return candidatoRepositorio.findAll().stream()
                .map(CandidatoDTO::new)
                .collect(Collectors.toList());
    }

    public CandidatoDTO crearCandidato(Candidato candidato) {
    	
    	if (!validarNifNie(candidato.getNifNie())) {
            throw new RuntimeException("El NIF/NIE proporcionado no es válido.");
        }
    	
        // Validar TipoPerfil
        if (candidato.getTipoPerfil() != null && candidato.getTipoPerfil().getId() != null) {
            TipoPerfil tipoPerfil = tipoPerfilRepositorio.findById(candidato.getTipoPerfil().getId())
                    .orElseThrow(() -> new RuntimeException("TipoPerfil no encontrado: " + candidato.getTipoPerfil().getId()));
            candidato.setTipoPerfil(tipoPerfil);
            System.out.println("TipoPerfil asignado: " + tipoPerfil.getNombre());
        }

        // Validar ModalidadProvincia
        if (candidato.getModalidadProvincia() != null && candidato.getModalidadProvincia().getId() != null) {
            ModalidadProvincia modalidadProvincia = modalidadProvinciaRepositorio.findById(candidato.getModalidadProvincia().getId())
                    .orElseThrow(() -> new RuntimeException("ModalidadProvincia no encontrada: " + candidato.getModalidadProvincia().getId()));
            candidato.setModalidadProvincia(modalidadProvincia);
            System.out.println("ModalidadProvincia asignada: " + modalidadProvincia.getId());
        }

        // Validar Tecnologias
        if (candidato.getTecnologias() != null && !candidato.getTecnologias().isEmpty()) {
            Set<Tecnologia> tecnologias = candidato.getTecnologias().stream()
                    .map(tecnologia -> tecnologiaRepositorio.findById(tecnologia.getId())
                            .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada: " + tecnologia.getId())))
                    .collect(Collectors.toSet());
            candidato.setTecnologias(tecnologias);
            tecnologias.forEach(tec -> System.out.println("Tecnologia asignada: " + tec.getId()));
        }

        // Guardar el candidato con las asociaciones
        Candidato savedCandidato = candidatoRepositorio.save(candidato);
        System.out.println("Candidato guardado con ID: " + savedCandidato.getId());

        // Convertir la entidad guardada a DTO antes de devolverla
        return new CandidatoDTO(savedCandidato);
    }

    public CandidatoDTO actualizarCandidato(Long id, Candidato candidato) {
    	if (!validarNifNie(candidato.getNifNie())) {
            throw new RuntimeException("El NIF/NIE proporcionado no es válido.");
        }
    	
        // Buscar candidato existente
        Candidato candidatoExistente = candidatoRepositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidato no encontrado con ID: " + id));

        // Actualizar los campos básicos
        candidatoExistente.setNombre(candidato.getNombre());
        candidatoExistente.setApellidos(candidato.getApellidos());
        candidatoExistente.setCorreo(candidato.getCorreo());
        candidatoExistente.setTelefono(candidato.getTelefono());
        candidatoExistente.setNifNie(candidato.getNifNie());
        candidatoExistente.setSalario(candidato.getSalario());
        candidatoExistente.setCv(candidato.getCv());
        candidatoExistente.setFechaNacimiento(candidato.getFechaNacimiento());
        candidatoExistente.setEstado(candidato.getEstado());
        candidatoExistente.setGenero(candidato.getGenero());
        candidatoExistente.setNivelIngles(candidato.getNivelIngles());
        candidatoExistente.setFuenteReclutamiento(candidato.getFuenteReclutamiento());

        // Actualizar TipoPerfil si se proporciona
        if (candidato.getTipoPerfil() != null && candidato.getTipoPerfil().getId() != null) {
            TipoPerfil tipoPerfil = tipoPerfilRepositorio.findById(candidato.getTipoPerfil().getId())
                .orElseThrow(() -> new RuntimeException("TipoPerfil no encontrado: " + candidato.getTipoPerfil().getId()));
            candidatoExistente.setTipoPerfil(tipoPerfil);
            System.out.println("TipoPerfil actualizado: " + tipoPerfil.getNombre());
        }

        // Actualizar ModalidadProvincia si se proporciona
        if (candidato.getModalidadProvincia() != null && candidato.getModalidadProvincia().getId() != null) {
            ModalidadProvincia modalidadProvincia = modalidadProvinciaRepositorio.findById(candidato.getModalidadProvincia().getId())
                .orElseThrow(() -> new RuntimeException("ModalidadProvincia no encontrada: " + candidato.getModalidadProvincia().getId()));
            candidatoExistente.setModalidadProvincia(modalidadProvincia);
            System.out.println("ModalidadProvincia actualizada: " + modalidadProvincia.getId());
        }

        // Actualizar Tecnologías si se proporciona
        if (candidato.getTecnologias() != null && !candidato.getTecnologias().isEmpty()) {
            Set<Tecnologia> tecnologias = candidato.getTecnologias().stream()
                .map(tecnologia -> tecnologiaRepositorio.findById(tecnologia.getId())
                    .orElseThrow(() -> new RuntimeException("Tecnologia no encontrada: " + tecnologia.getId())))
                .collect(Collectors.toSet());
            candidatoExistente.setTecnologias(tecnologias);
            tecnologias.forEach(tec -> System.out.println("Tecnología actualizada: " + tec.getId()));
        }

        // Guardar candidato actualizado
        Candidato candidatoActualizado = candidatoRepositorio.save(candidatoExistente);
        System.out.println("Candidato actualizado con ID: " + candidatoActualizado.getId());

        // Convertir a DTO y devolver
        return new CandidatoDTO(candidatoActualizado);
    }
    
    private boolean validarNifNie(String nifNie) {
        if (nifNie == null || nifNie.isEmpty()) {
            return false;
        }

        // Expresión regular para validar NIF (DNI) y NIE
        String regex = "^[XYZ]?[0-9]{7,8}[A-Z]$";

        return nifNie.matches(regex);
    }

    public CandidatoDTO obtenerPorId(Long id) {
        Candidato candidato = candidatoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con ID: " + id));
        return new CandidatoDTO(candidato);
    }

    public void eliminarCandidato(Long id) {
        if (!candidatoRepositorio.existsById(id)) {
            throw new RuntimeException("Candidato no encontrado con ID: " + id);
        }
        candidatoRepositorio.deleteById(id);
    }
}