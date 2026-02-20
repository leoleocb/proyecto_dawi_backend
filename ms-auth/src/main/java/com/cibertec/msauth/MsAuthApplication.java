package com.cibertec.msauth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cibertec.gestionmedica.entity.Usuario;
import com.cibertec.msauth.repository.UsuarioRepository;

@SpringBootApplication
@EntityScan(basePackages = {"com.cibertec.gestionmedica.entity"})


@EnableJpaRepositories(basePackages = {"com.cibertec.msauth.repository"})
public class MsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAuthApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner initData(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			if (usuarioRepository.findByName("admin").isEmpty()) {
				
				Usuario user = new Usuario();
				user.setName("admin");
				user.setEmail("admin@clinica.com");
				
				user.setPassword(passwordEncoder.encode("123456")); 
				
				usuarioRepository.save(user);
				System.out.println(">>> USUARIO DE PRUEBA CREADO: admin / 123456 <<<");
			}
		};
	}
	
	
}
