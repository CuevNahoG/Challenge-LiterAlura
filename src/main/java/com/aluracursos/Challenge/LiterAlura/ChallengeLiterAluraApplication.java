package com.aluracursos.Challenge.LiterAlura;

import com.aluracursos.Challenge.LiterAlura.Principal.BuscarLibroException;
import com.aluracursos.Challenge.LiterAlura.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	private final Principal principal;

	public ChallengeLiterAluraApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
        try {
            principal.muestraElMenu();
        } catch (BuscarLibroException e) {
            throw new RuntimeException(e);
        }
    }
}
