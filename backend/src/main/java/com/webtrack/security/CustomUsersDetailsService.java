package com.webtrack.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webtrack.model.Rol;
import com.webtrack.model.Usuario;
import com.webtrack.repository.UsuarioRepositorio;

/**
 * Servicio personalizado para gestionar la autenticación de usuarios.
 * Implementa la interfaz UserDetailService de Spring Security.
 */

@Service
public class CustomUsersDetailsService implements UserDetailsService{
	
	// Repositorio de usuarios para la gestión de autenticación
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	public CustomUsersDetailsService(UsuarioRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}
	
	/**
	 * Convierte los roles del usuario en autoridades reconocibles por Spring Security.
	 * @param roles Conjunto de roles del usuario
	 * @return Lista de autoridades (GrantedAuthority)
	 */
	public Collection<GrantedAuthority> mapToAutorities(Set<Rol> set){
		return set.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
	
	/**
	 * Carga los detalles del usuario según el nombre de usuario proporcionado
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		return new User(usuario.getUsername(), usuario.getPassword(), mapToAutorities(usuario.getRoles()));
	}

	

}
