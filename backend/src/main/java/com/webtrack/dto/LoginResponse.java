package com.webtrack.dto;

import java.util.List;

/**
 * DTO (Data Transfer Object) para manejar las respuestas de inicio de sesión.
 * Incluye el token de acceso, el tipo de token, el ID del usuario y los roles asociados.
 */

public class LoginResponse {
    private String accessToken; // Token de acceso generado para la autenticación
    private String tokenType = "Bearer "; // Tipo de token (por defecto Bearer)
    private Long userId; // ID del usuario que ha iniciado sesión
    private List<String> roles; // Lista de roles asociados al usuario
    
    public LoginResponse(String accessToken, Long userId, List<String> roles) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUserId() { 
        return userId;
    }

    public void setUserId(Long userId) { 
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
