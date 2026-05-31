package com.clinica.saludplus;

import com.clinica.saludplus.model.Role;
import com.clinica.saludplus.model.User;
import com.clinica.saludplus.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SaludplusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaludplusApplication.class, args);
	}

	@Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByUsername("admin").isEmpty()) {
				User user = new User();
				user.setUsername("admin");
				user.setPassword(encoder.encode("1234"));
				user.setRole(Role.ROLE_ADMIN);
				repo.save(user);
			}
		};
	}



}
