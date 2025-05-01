package com.webtrack.dto;

/**
 * DTO (Data Transfer Object) para manejar las solicitudes de inicio de sesión.
 * Incluye el nombre de usuario y la contraseña.
 */

public class LoginRequest {
	private String username;
    private String password;

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
