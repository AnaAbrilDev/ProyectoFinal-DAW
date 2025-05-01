package com.webtrack.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Clase encargada de generar, extraer y validar tokens JWT.
 * Utiliza una clave secreta basada en HS512 para firmar los tokens.
 */

@Component
public class JwtGenerador {
	
	// Clave secreta para la firma del token
    private final SecretKey signingKey;
    
    // Constructor que decodifica la clave secreta desde la constante
    public JwtGenerador() {
        this.signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(ConstantesSeguridad.JWT_FIRMA));
    }
	
	// Metodo para crear token
 // Método para crear token
    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(tiempoActual)
                .setExpiration(expiracionToken)
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }
	
    public String obtenerUsernameDeJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
	
    public Boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o está incorrecto", e);
        }
    }
}
