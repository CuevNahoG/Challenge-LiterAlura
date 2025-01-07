package com.aluracursos.Challenge.LiterAlura.Repository;

import com.aluracursos.Challenge.LiterAlura.Model.CategoriaIdioma;
import com.aluracursos.Challenge.LiterAlura.Model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findLibroBytitulo(String titulo);

    List<Libros> findLibrosByidioma(CategoriaIdioma idioma);
}

