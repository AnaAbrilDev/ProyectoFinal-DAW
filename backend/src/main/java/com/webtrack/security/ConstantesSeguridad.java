package com.webtrack.security;

/**
 * Constantes relacionadas con la seguridad del sistema.
 * Incluye la configuración del tiempo de expiración del token JWT
 * y la clave de firma utilizada para su generación.
 */

public class ConstantesSeguridad {
	public static final long JWT_EXPIRATION_TOKEN = 86400000;
	public static final String JWT_FIRMA = "J10mF28dqoSpWefzoEBO2pZXkmImKLfMCDaBjI2vcpOtR5S4JyJlAPsCea9wFXcd0UUBX5F5KLqZd0hnDPjiGA==";
}
