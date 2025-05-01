package com.webtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.webtrack.model") 
@EnableJpaRepositories("com.webtrack.repository") 
public class WebtrackApplication {

    public static void main(String[] args) {
    	/*Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Clave segura generada: " + base64Key);*/
    	
        SpringApplication.run(WebtrackApplication.class, args); // Inicia la aplicación
    }
}

