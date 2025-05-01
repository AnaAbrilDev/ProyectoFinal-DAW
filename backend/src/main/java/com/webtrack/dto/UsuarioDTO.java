package com.webtrack.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.webtrack.model.Usuario;

/**
 * DTO (Data Transfer Object) para transferir datos simplificados de un usuario.
 * Este DTO se utiliza para enviar información básica del usuario en las respuestas de la API,
 * excluyendo información sensible como contraseñas.
 */

public class UsuarioDTO {
	private Long id;
	private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private Set<String> roles; 
    
    // Constructor
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.nombre = usuario.getNombre();
        this.apellidos = usuario.getApellidos();
        this.email = usuario.getEmail();
        
        if (usuario.getRoles() != null) {
            this.roles = usuario.getRoles().stream()
                    .map(rol -> rol.getNombre())
                    .collect(Collectors.toSet());
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}
