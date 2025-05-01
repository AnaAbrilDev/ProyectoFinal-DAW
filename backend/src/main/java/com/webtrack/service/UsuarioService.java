package com.webtrack.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webtrack.dto.UsuarioDTO;
import com.webtrack.model.Rol;
import com.webtrack.model.Usuario;
import com.webtrack.repository.RolRepositorio;
import com.webtrack.repository.UsuarioRepositorio;

/**
 * Servicio para gestionar las operaciones relacionadas con la entidad Usuario.
 * Proporciona funciones para crear, leer, actualizar y eliminar usuarios,
 * además de validaciones para evitar duplicados y gestionar roles asociados.
 */

@Service
public class UsuarioService {

	// Repositorios y componentes para gestionar la lógica de negocio
    private final UsuarioRepositorio usuarioRepositorio;
    private final RolRepositorio rolRepositorio;
    private final PasswordEncoder passwordEncoder;


    // Constructor que inyecta las dependencias necesarias
    public UsuarioService(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.rolRepositorio = rolRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene todos los usuarios en formato DTO
     * @return Lista de UsuarioDTO
     */
    public List<UsuarioDTO> obtenerTodosUsuarios() {
        return usuarioRepositorio.findAll().stream()
                .map(UsuarioDTO::new) // Convertir cada entidad Usuario en UsuarioDTO
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su ID en formato DTO
     * @param id ID del usuario a consultar
     * @return Optional con el UsuarioDTO encontrado,o vacío si no se encuentra
     */
    public Optional<UsuarioDTO> obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .map(UsuarioDTO::new); // Convertir Usuario en UsuarioDTO
    }

    public UsuarioDTO crearUsuario(Usuario usuario) {
        //  Verificar si el username ya existe
        if (usuarioRepositorio.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }

        // Verificar si el email ya existe
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }

        // Convertir los roles enviados con solo IDs en entidades gestionadas
        List<Rol> roles = usuario.getRoles().stream()
                .map(rol -> rolRepositorio.findById(rol.getId())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rol.getId())))
                .collect(Collectors.toList());

        // Asignar roles gestionados al usuario
        usuario.setRoles(new HashSet<>(roles));

        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Guardar el usuario y retornar como DTO
        Usuario usuarioCreado = usuarioRepositorio.save(usuario);
        return new UsuarioDTO(usuarioCreado);
    }

    public UsuarioDTO actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepositorio.findById(id).map(usuario -> {
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setEmail(usuarioActualizado.getEmail());

            // Solo actualizar la contraseña si el usuario la envía
            if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
            }

            // Convertir los roles enviados con solo IDs en entidades gestionadas
            if (usuarioActualizado.getRoles() != null && !usuarioActualizado.getRoles().isEmpty()) {
                List<Rol> roles = usuarioActualizado.getRoles().stream()
                    .map(rol -> rolRepositorio.findById(rol.getId())
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + rol.getId())))
                    .collect(Collectors.toList());

                usuario.setRoles(new HashSet<>(roles));
            }

            Usuario usuarioActualizadoEntity = usuarioRepositorio.save(usuario);
            return new UsuarioDTO(usuarioActualizadoEntity);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }



    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }
}