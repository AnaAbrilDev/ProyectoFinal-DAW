package com.webtrack.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Clase que maneja las excepciones durante el proceso de autenticación.
 * Se activa cuando un usuario intenta acceder a un recurso protegido sin estar autenticado.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Método que gestiona los errores de autenticación y responde con un estado HTTP 401 (Unauthorized)
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}

}
