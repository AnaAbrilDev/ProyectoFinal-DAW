package com.webtrack.config;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webtrack.model.EstadoCandidatura;
import com.webtrack.model.Modalidad;
import com.webtrack.model.ModalidadProvincia;
import com.webtrack.model.Provincia;
import com.webtrack.model.Rol;
import com.webtrack.model.Tecnologia;
import com.webtrack.model.TipoPerfil;
import com.webtrack.model.Usuario;
import com.webtrack.repository.EstadoCandidaturaRepositorio;
import com.webtrack.repository.ModalidadProvinciaRepositorio;
import com.webtrack.repository.ModalidadRepositorio;
import com.webtrack.repository.ProvinciaRepositorio;
import com.webtrack.repository.RolRepositorio;
import com.webtrack.repository.TecnologiaRepositorio;
import com.webtrack.repository.TipoPerfilRepositorio;
import com.webtrack.repository.UsuarioRepositorio;

@Configuration
public class DatabaseSeeder {
	
	/**
	 * Controla si se ejecuta el seeding de la base de datos según
	 * el valor configurado en application.properties:
	 * seed.database.enabled = true (para habilitar el seeder)
	 */
	
	// Leer la propiedad desde el archivo de configuración
    @Value("${seed.database.enabled:true}")
    private boolean isSeedEnabled;

    @Bean
    CommandLineRunner initDatabase(
            RolRepositorio rolRepositorio,
            TipoPerfilRepositorio tipoPerfilRepositorio,
            TecnologiaRepositorio tecnologiaRepositorio,
            ProvinciaRepositorio provinciaRepositorio,
            ModalidadRepositorio modalidadRepositorio,
            ModalidadProvinciaRepositorio modalidadProvinciaRepositorio,
            EstadoCandidaturaRepositorio estadoCandidaturaRepositorio,
            UsuarioRepositorio usuarioRepositorio,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
        	// Verificar si el flag está habilitado
            if (!isSeedEnabled) {
                System.out.println("Seeding de base de datos deshabilitado.");
                return;
            }
            
        	// Insertar roles iniciales si no existen
        	if (rolRepositorio.count() == 0) {
        	    List<Rol> roles = List.of(
        	        new Rol("COORDINADOR"),
        	        new Rol("RECRUITER")
        	    );
        	    rolRepositorio.saveAll(roles);
        	    System.out.println("Roles iniciales creados: Coordinador y Recruiter");
        	}

        	// Verificar si existe el rol "Coordinador"
        	Rol coordinadorRol = rolRepositorio.findById(1L)
        	    .orElseThrow(() -> new RuntimeException("Rol Coordinador no encontrado."));

        	// Crear el usuario "Coordinador" si no existe ningún usuario
        	if (usuarioRepositorio.count() == 0) {
        	    Usuario nuevoUsuario = new Usuario();
        	    nuevoUsuario.setUsername("admin");
        	    nuevoUsuario.setNombre("Admin");
        	    nuevoUsuario.setApellidos("Coordinador");
        	    nuevoUsuario.setEmail("coordinador@example.com");
        	    nuevoUsuario.setPassword(passwordEncoder.encode("password123"));

        	    // Asignar el rol gestionado
        	    nuevoUsuario.setRoles(Set.of(coordinadorRol));

        	    // Guardar el usuario
        	    usuarioRepositorio.save(nuevoUsuario);
        	    System.out.println("Usuario Coordinador creado con éxito.");
        	} else {
        	    System.out.println("Usuario Coordinador ya existe.");
        	}

            // Insertar tipos de perfil iniciales
            if (tipoPerfilRepositorio.findAll().isEmpty()) {
            	List<TipoPerfil> tiposPerfil = List.of(
            		    new TipoPerfil("ADMINISTRADOR BASE DATOS"),
            		    new TipoPerfil("ADMIN INGENIERO SISTEMAS"),
            		    new TipoPerfil("AGILE COACH"),
            		    new TipoPerfil("ANALISTA FUNCIONAL"),
            		    new TipoPerfil("ANALISTA NEGOCIO"),
            		    new TipoPerfil("ANALISTA ORGANICO"),
            		    new TipoPerfil("ANALISTA PROGRAMADOR"),
            		    new TipoPerfil("ARQUITECTO SISTEMAS"),
            		    new TipoPerfil("ARQUITECTO SOFTWARE"),
            		    new TipoPerfil("CONSULTOR IT"),
            		    new TipoPerfil("COORDINADOR PROYECTO"),
            		    new TipoPerfil("DATA ANALYST"),
            		    new TipoPerfil("DATA SCIENTIST"),
            		    new TipoPerfil("DESARROLLADOR BACKEND"),
            		    new TipoPerfil("DESARROLLADOR FRONTEND"),
            		    new TipoPerfil("DISEÑADOR WEB GRAFICO"),
            		    new TipoPerfil("ESPECIALISTA BIG DATA"),
            		    new TipoPerfil("ESPECIALISTA IA"),
            		    new TipoPerfil("ESPECIALISTA MACHINE LEARNING"),
            		    new TipoPerfil("ESPECIALISTA TELECO REDES"),
            		    new TipoPerfil("ESPECIALISTA VIRTUALIZACION"),
            		    new TipoPerfil("FULLSTACK"),
            		    new TipoPerfil("INGENIERO CIBERSEGURIDAD"),
            		    new TipoPerfil("INGENIERO DEVOPS"),
            		    new TipoPerfil("JEFE EQUIPO"),
            		    new TipoPerfil("PMO"),
            		    new TipoPerfil("PROGRAMADOR BBDD"),
            		    new TipoPerfil("QA TESTER"),
            		    new TipoPerfil("SCRUM MASTER"),
            		    new TipoPerfil("SOPORTE HELPDESK"),
            		    new TipoPerfil("TECNICO CAU"),
            		    new TipoPerfil("TECNICO SISTEMAS"),
            		    new TipoPerfil("UX UI")
            		);
                tipoPerfilRepositorio.saveAll(tiposPerfil);
                System.out.println("Tipos de perfil iniciales creados.");
            }
            
         // Insertar tecnologías iniciales
            if (tecnologiaRepositorio.findAll().isEmpty()) {
            	List<Tecnologia> tecnologias = List.of(
            		    new Tecnologia("ABAP"),
            		    new Tecnologia("ACCESS"),
            		    new Tecnologia("ACTIVE DIRECTORY"),
            		    new Tecnologia("ADOBE ILLUSTRATOR"),
            		    new Tecnologia("ADOBE PHOTOSHOP"),
            		    new Tecnologia("AGILE FRAMEWORK"),
            		    new Tecnologia("AI"),
            		    new Tecnologia("AIRFLOW"),
            		    new Tecnologia("AJAX"),
            		    new Tecnologia("ANDROID"),
            		    new Tecnologia("ANGULAR"),
            		    new Tecnologia("ANGULARJS"),
            		    new Tecnologia("ANSIBLE"),
            		    new Tecnologia("APACHE"),
            		    new Tecnologia("APEX"),
            		    new Tecnologia("API REST"),
            		    new Tecnologia("APPIUM"),
            		    new Tecnologia("APX"),
            		    new Tecnologia("ASP NET"),
            		    new Tecnologia("AS 400"),
            		    new Tecnologia("ATLASSIAN"),
            		    new Tecnologia("AUTOCAD"),
            		    new Tecnologia("AWS"),
            		    new Tecnologia("AZURE"),
            		    new Tecnologia("BATCH"),
            		    new Tecnologia("BI"),
            		    new Tecnologia("BIG DATA"),
            		    new Tecnologia("BITBUCKET"),
            		    new Tecnologia("BOOTSTRAP"),
            		    new Tecnologia("C"),
            		    new Tecnologia("CASSANDRA"),
            		    new Tecnologia("CENTOS"),
            		    new Tecnologia("CIBERSEGURIDAD"),
            		    new Tecnologia("CISCO"),
            		    new Tecnologia("CITRIX"),
            		    new Tecnologia("CI CD"),
            		    new Tecnologia("CLOUD"),
            		    new Tecnologia("CNNA"),
            		    new Tecnologia("COBOL"),
            		    new Tecnologia("CONFLUENCE"),
            		    new Tecnologia("CORDOVA"),
            		    new Tecnologia("CSS"),
            		    new Tecnologia("CUCUMBER"),
            		    new Tecnologia("CYPRESS"),
            		    new Tecnologia("C PLUS PLUS"),
            		    new Tecnologia("C SHARP"),
            		    new Tecnologia("DDD"),
            		    new Tecnologia("DEBIAN"),
            		    new Tecnologia("DESIGN THINKING"),
            		    new Tecnologia("DEVOPS"),
            		    new Tecnologia("DJANGO"),
            		    new Tecnologia("DNS"),
            		    new Tecnologia("DOCKER"),
            		    new Tecnologia("DOT NET"),
            		    new Tecnologia("DOT NET CORE"),
            		    new Tecnologia("DRUPAL"),
            		    new Tecnologia("DYNATRACE"),
            		    new Tecnologia("ECLIPSE"),
            		    new Tecnologia("ELASTIC SEARCH"),
            		    new Tecnologia("EMBEBIDO"),
            		    new Tecnologia("ENTITY FRAMEWORK"),
            		    new Tecnologia("ETL"),
            		    new Tecnologia("EXCEL"),
            		    new Tecnologia("EXCHANGE"),
            		    new Tecnologia("EXPERIAN"),
            		    new Tecnologia("FILENET"),
            		    new Tecnologia("FORMS REPORTS"),
            		    new Tecnologia("FORTINET"),
            		    new Tecnologia("GCP"),
            		    new Tecnologia("GIS"),
            		    new Tecnologia("GIT"),
            		    new Tecnologia("GITLAB"),
            		    new Tecnologia("GO"),
            		    new Tecnologia("GRAFANA"),
            		    new Tecnologia("GROOVY"),
            		    new Tecnologia("HADOOP"),
            		    new Tecnologia("HANA4"),
            		    new Tecnologia("HIBERNATE"),
            		    new Tecnologia("HOST"),
            		    new Tecnologia("HTML"),
            		    new Tecnologia("HYPER V"),
            		    new Tecnologia("IAC"),
            		    new Tecnologia("IONIC"),
            		    new Tecnologia("IOS"),
            		    new Tecnologia("IOT"),
            		    new Tecnologia("ITIL"),
            		    new Tecnologia("ITSM"),
            		    new Tecnologia("JAVA"),
            		    new Tecnologia("JAVASCRIPT"),
            		    new Tecnologia("JBOSS"),
            		    new Tecnologia("JENKINS"),
            		    new Tecnologia("JEST"),
            		    new Tecnologia("JIRA"),
            		    new Tecnologia("JMETER"),
            		    new Tecnologia("JOOMLA"),
            		    new Tecnologia("JQUERY"),
            		    new Tecnologia("JSF"),
            		    new Tecnologia("JSON"),
            		    new Tecnologia("JUNIT"),
            		    new Tecnologia("KAFKA"),
            		    new Tecnologia("KANBAN"),
            		    new Tecnologia("KIBANA"),
            		    new Tecnologia("KOTLIN"),
            		    new Tecnologia("KUBERNETES"),
            		    new Tecnologia("LAMBDA"),
            		    new Tecnologia("LARAVEL"),
            		    new Tecnologia("LEAN"),
            		    new Tecnologia("LIFERAY"),
            		    new Tecnologia("LIGHTNING"),
            		    new Tecnologia("LINUX"),
            		    new Tecnologia("LOGSTASH"),
            		    new Tecnologia("MAC"),
            		    new Tecnologia("MAGENTO"),
            		    new Tecnologia("MAINFRAME"),
            		    new Tecnologia("MANAGEMENT 30"),
            		    new Tecnologia("MARIADB"),
            		    new Tecnologia("MAVEN"),
            		    new Tecnologia("MICROSERVICIOS"),
            		    new Tecnologia("MICROSTRATEGY"),
            		    new Tecnologia("MOCKITO"),
            		    new Tecnologia("MODELLICA"),
            		    new Tecnologia("MONGODB"),
            		    new Tecnologia("MOODLE"),
            		    new Tecnologia("MS SQL"),
            		    new Tecnologia("MULESOFT"),
            		    new Tecnologia("MUREX"),
            		    new Tecnologia("MVC"),
            		    new Tecnologia("MYSQL"),
            		    new Tecnologia("NAGIOS"),
            		    new Tecnologia("NATURAL ADABAS"),
            		    new Tecnologia("NAVISION"),
            		    new Tecnologia("NODEJS"),
            		    new Tecnologia("ODOO"),
            		    new Tecnologia("OFFICE 365"),
            		    new Tecnologia("OPENSHIFT"),
            		    new Tecnologia("ORACLE"),
            		    new Tecnologia("PALO ALTO"),
            		    new Tecnologia("PENTAHO"),
            		    new Tecnologia("PENTESTING"),
            		    new Tecnologia("PHP"),
            		    new Tecnologia("PL SQL"),
            		    new Tecnologia("PMP"),
            		    new Tecnologia("POSTGRESQL"),
            		    new Tecnologia("POSTMAN"),
            		    new Tecnologia("POWERBI"),
            		    new Tecnologia("POWERSHELL"),
            		    new Tecnologia("PRIMEFACES"),
            		    new Tecnologia("PUPPET"),
            		    new Tecnologia("PYSPARK"),
            		    new Tecnologia("PYTHON"),
            		    new Tecnologia("QLIKVIEW"),
            		    new Tecnologia("R"),
            		    new Tecnologia("REACT"),
            		    new Tecnologia("REDES WLAN LAN"),
            		    new Tecnologia("REDIS"),
            		    new Tecnologia("REDMINE"),
            		    new Tecnologia("REDUX"),
            		    new Tecnologia("RED HAT"),
            		    new Tecnologia("REMEDY"),
            		    new Tecnologia("RESTFUL"),
            		    new Tecnologia("ROBOT FRAMEWORK"),
            		    new Tecnologia("ROUTER SWITCH"),
            		    new Tecnologia("RPA"),
            		    new Tecnologia("RPG"),
            		    new Tecnologia("RUBY"),
            		    new Tecnologia("SAFE"),
            		    new Tecnologia("SALESFORCE"),
            		    new Tecnologia("SAP"),
            		    new Tecnologia("SAP ABAP"),
            		    new Tecnologia("SASS"),
            		    new Tecnologia("SCALA"),
            		    new Tecnologia("SCRUM"),
            		    new Tecnologia("SELENIUM"),
            		    new Tecnologia("SERVICE NOW"),
            		    new Tecnologia("SHOPIFY"),
            		    new Tecnologia("SISS"),
            		    new Tecnologia("SOAP"),
            		    new Tecnologia("SOCKETS"),
            		    new Tecnologia("SOLARIS"),
            		    new Tecnologia("SOLID"),
            		    new Tecnologia("SONARQUBE"),
            		    new Tecnologia("SPARK"),
            		    new Tecnologia("SPRING"),
            		    new Tecnologia("SPRINGBOOT"),
            		    new Tecnologia("SPRINGCLOUD"),
            		    new Tecnologia("SPRINGDATA"),
            		    new Tecnologia("SQL"),
            		    new Tecnologia("SQL SERVER"),
            		    new Tecnologia("STRUTS"),
            		    new Tecnologia("SVN"),
            		    new Tecnologia("SWIFT"),
            		    new Tecnologia("SYMFONY"),
            		    new Tecnologia("TABLEAU"),
            		    new Tecnologia("TCP IP"),
            		    new Tecnologia("TERRAFORM"),
            		    new Tecnologia("TESTLINK"),
            		    new Tecnologia("TESTRAIL"),
            		    new Tecnologia("TIBCO"),
            		    new Tecnologia("TOMCAT"),
            		    new Tecnologia("TYPESCRIPT"),
            		    new Tecnologia("UBUNTU"),
            		    new Tecnologia("UI"),
            		    new Tecnologia("UNITY"),
            		    new Tecnologia("UNIX"),
            		    new Tecnologia("UX"),
            		    new Tecnologia("VANILLA"),
            		    new Tecnologia("VISUAL BASIC"),
            		    new Tecnologia("VMWARE"),
            		    new Tecnologia("VOIP"),
            		    new Tecnologia("VPN"),
            		    new Tecnologia("WEBCOMPONENTS"),
            		    new Tecnologia("WILDFLY"),
            		    new Tecnologia("WINDOWS SERVER"),
            		    new Tecnologia("WORDPRESS"),
            		    new Tecnologia("XML"),
            		    new Tecnologia("ZOZ")
            		);
                tecnologiaRepositorio.saveAll(tecnologias);
                System.out.println("Tecnologías iniciales creadas.");
            }
            
         // Insertar provincias iniciales
            if (provinciaRepositorio.findAll().isEmpty()) {
                List<Provincia> provincias = List.of(
                        new Provincia("ALAVA"),
                        new Provincia("ALBACETE"),
                        new Provincia("ALICANTE"),
                        new Provincia("ALMERIA"),
                        new Provincia("ASTURIAS"),
                        new Provincia("AVILA"),
                        new Provincia("BADAJOZ"),
                        new Provincia("BALEARES"),
                        new Provincia("BARCELONA"),
                        new Provincia("BIZKAIA"),
                        new Provincia("BURGOS"),
                        new Provincia("CACERES"),
                        new Provincia("CADIZ"),
                        new Provincia("CANTABRIA"),
                        new Provincia("CASTELLON"),
                        new Provincia("CEUTA"),
                        new Provincia("CIUDAD_REAL"),
                        new Provincia("CORDOBA"),
                        new Provincia("CORUNA"),
                        new Provincia("CUENCA"),
                        new Provincia("GIPUZKOA"),
                        new Provincia("GIRONA"),
                        new Provincia("GRANADA"),
                        new Provincia("GUADALAJARA"),
                        new Provincia("HUELVA"),
                        new Provincia("HUESCA"),
                        new Provincia("JAEN"),
                        new Provincia("LEON"),
                        new Provincia("LLEIDA"),
                        new Provincia("LUGO"),
                        new Provincia("MADRID"),
                        new Provincia("MALAGA"),
                        new Provincia("MELILLA"),
                        new Provincia("MURCIA"),
                        new Provincia("NAVARRA"),
                        new Provincia("OURENSE"),
                        new Provincia("PALENCIA"),
                        new Provincia("PALMAS"),
                        new Provincia("PONTEVEDRA"),
                        new Provincia("RIOJA"),
                        new Provincia("SALAMANCA"),
                        new Provincia("SANTA CRUZ DE TENERIFE"),
                        new Provincia("SEGOVIA"),
                        new Provincia("SEVILLA"),
                        new Provincia("SORIA"),
                        new Provincia("TARRAGONA"),
                        new Provincia("TERUEL"),
                        new Provincia("TOLEDO"),
                        new Provincia("VALENCIA"),
                        new Provincia("VALLADOLID"),
                        new Provincia("ZAMORA"),
                        new Provincia("ZARAGOZA")
                );
                provinciaRepositorio.saveAll(provincias);
                System.out.println("Provincias iniciales creadas.");
            }
            
         // Insertar modalidades iniciales
            if (modalidadRepositorio.findAll().isEmpty()) {
                List<Modalidad> modalidades = List.of(
                        new Modalidad("HIBRIDO"),
                        new Modalidad("PRESENCIAL"),
                        new Modalidad("REMOTO")
                );
                modalidadRepositorio.saveAll(modalidades);
                System.out.println("Modalidades iniciales creadas: HÍBRIDO, PRESENCIAL, REMOTO.");
            }
            
         // Asignar cada provincia a cada modalidad
            if (modalidadProvinciaRepositorio.findAll().isEmpty()) {
                List<Provincia> provincias = provinciaRepositorio.findAll();
                List<Modalidad> modalidades = modalidadRepositorio.findAll();

                List<ModalidadProvincia> modalidadProvincias = provincias.stream()
                        .flatMap(provincia -> modalidades.stream()
                                .map(modalidad -> new ModalidadProvincia(provincia, modalidad)))
                        .toList();

                modalidadProvinciaRepositorio.saveAll(modalidadProvincias);
                System.out.println("Todas las provincias han sido asignadas a todas las modalidades.");
            }
            
            if (estadoCandidaturaRepositorio.findAll().isEmpty()) {
            	List<EstadoCandidatura> estadoCandidaturas = List.of(
            		    new EstadoCandidatura("NUEVA"),
            		    new EstadoCandidatura("EN REVISION"),
            		    new EstadoCandidatura("PRESELECCIONADA"),
            		    new EstadoCandidatura("ENTREVISTA PROGRAMADA"),
            		    new EstadoCandidatura("ENTREVISTA REALIZADA"),
            		    new EstadoCandidatura("EN EVALUACION FINAL"),
            		    new EstadoCandidatura("SELECCIONADA"),
            		    new EstadoCandidatura("RECHAZADA"),
            		    new EstadoCandidatura("DESISTIDA"),
            		    new EstadoCandidatura("CONTRATADA"),
            		    new EstadoCandidatura("EN ESPERA")
            		);
                estadoCandidaturaRepositorio.saveAll(estadoCandidaturas);
            }

        };
    }
}