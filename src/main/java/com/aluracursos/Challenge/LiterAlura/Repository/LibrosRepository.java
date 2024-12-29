package com.aluracursos.Challenge.LiterAlura.Repository;

import com.aluracursos.Challenge.LiterAlura.Model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findLibroBytituloContainsIgnoreCase(String titulo);

    List<Libros> findByIdiomaContaining(String Idioma);

    Libros findByTitulo(String titulo);
}
