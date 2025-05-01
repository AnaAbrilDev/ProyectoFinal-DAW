package com.webtrack.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro personalizado para la autenticación de peticiiones utilizando JWT.
 * Este filtro se ejecuta una vez por cada solicitud (extiende OncePerRequestFilter)
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUsersDetailsService customUsersDetailsService;
    
    @Autowired
    private JwtGenerador jwtGenerador;
    
    /**
     * Obtiene el token JWT de la cabecera "Authorization" de la solicitud.
     * @param request La solicitud HTTP entrante
     * @return El token JWT si está presente, de lo contrario devuelve null
     */
    
    public String obtenerTokenDeSolicitud(HttpServletRequest request) {
    	String bearerToken = request.getHeader("Authorization");
    	// Verifica si el token tiene texto y comienza con "Bearer "
    	if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
    		return bearerToken.substring(7, bearerToken.length());
    	}
    	return null;
    }

    /**
     * Método principal del filtro que se ejecuta en cada solicitud.
     * Gestiona la validación del token JWT y la autenticación del usuario.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	// Extraer el token de la solicitud
        String token = obtenerTokenDeSolicitud(request);
        
        // Verificar si el token es válido
        if(StringUtils.hasLength(token) && jwtGenerador.validarToken(token)) {
        	// Obtener el nombre de usuario a partir del token
        	String username = jwtGenerador.obtenerUsernameDeJwt(token);
        	
        	// Cargar los detalles del usuario desde la base de datos
        	UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);
        	
        	// Extraer los roles del usuario
        	List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        	
        	// Si el usuario tiene un rol válido (COORDINADOR o RECRUITER), establecer autenticación
        	if(userRoles.contains("COORDINADOR") || userRoles.contains("RECRUITER")) {
        		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        	}
        }
        filterChain.doFilter(request, response);
    }

}