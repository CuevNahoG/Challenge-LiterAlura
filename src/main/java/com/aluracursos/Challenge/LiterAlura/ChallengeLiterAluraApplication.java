package com.aluracursos.Challenge.LiterAlura;

import com.aluracursos.Challenge.LiterAlura.Principal.Principal;
import com.aluracursos.Challenge.LiterAlura.Repository.AutoresRepository;
import com.aluracursos.Challenge.LiterAlura.Repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	@Autowired
	private AutoresRepository autoresRepository;

	@Autowired
	private LibrosRepository librosRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autoresRepository, librosRepository);
		principal.muestramenu();
	}
}
