package com.webtrack.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un usuario en el sistema.
 * Cada usuario se almacena en la tabla "usuarios" en la base de datos.
 * Un usuario puede tener uno o varios roles asignados.
 */

@Entity
@Table(name = "usuarios")
public class Usuario {

	// Identificador único del usuario
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	/**
	 * Nombre de usuario (username)
	 * Campo único y obligatorio
	 */
	@Column(unique = true, nullable = false)
	private String username;

	/**
	 * Nombre del usuario.
	 * Campo obligatorio con un límite de 50 caracteres.
	 */
    @NotEmpty
    @Size(max = 50)
    @Column(nullable = false)
    private String nombre;

    @NotEmpty
    @Size(max = 50)
    @Column(nullable = false)
    private String apellidos;

    @NotEmpty
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
    )
    private Set<Rol> roles = new HashSet<>();

    // Constructor sin argumentos
    public Usuario() {
    }

    // Constructor con argumentos
    public Usuario(String username, String nombre, String apellidos, String email, String password) {
    	this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
    }

    // Getters y setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	// Método toString
    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}



