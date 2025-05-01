package com.webtrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webtrack.dto.LoginRequest;
import com.webtrack.dto.LoginResponse;
import com.webtrack.model.Rol;
import com.webtrack.model.Usuario;
import com.webtrack.repository.RolRepositorio;
import com.webtrack.repository.UsuarioRepositorio;
import com.webtrack.security.JwtGenerador;

/**
 * Controlador encargado de gestionar la autenticación de usuarios.
 * Permite realizar el login y generar tokens JWT para sesiones seguras.
 */

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	
	// Manejador de autenticación de Spring Security
	private AuthenticationManager authenticationManager;
	private UsuarioRepositorio usuarioRepositorio;
	private JwtGenerador jwtGenerador;
	
	@Autowired
	
	public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			RolRepositorio rolRepositorio, UsuarioRepositorio usuarioRepositorio, JwtGenerador jwtGenerador) {
		this.authenticationManager = authenticationManager;
		this.usuarioRepositorio = usuarioRepositorio;
		this.jwtGenerador = jwtGenerador;
	}
	
	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
	    );
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String token = jwtGenerador.generarToken(authentication);

	    // Buscar el usuario en la base de datos
	    Usuario usuario = usuarioRepositorio.findByUsername(loginRequest.getUsername())
	            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    // Extraer el ID y los roles del usuario
	    Long userId = usuario.getId(); // ✅ Obtener el ID del usuario
	    List<String> roles = usuario.getRoles().stream()
	            .map(Rol::getNombre)
	            .toList();

	    // Retornar la respuesta con el token, ID del usuario y roles
	    return new ResponseEntity<>(new LoginResponse(token, userId, roles), HttpStatus.OK);
	}

}
