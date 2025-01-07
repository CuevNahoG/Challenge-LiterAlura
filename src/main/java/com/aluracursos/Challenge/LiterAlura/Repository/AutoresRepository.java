package com.aluracursos.Challenge.LiterAlura.Repository;

import com.aluracursos.Challenge.LiterAlura.Model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autores, Long> {
    Optional<Autores> findBynombre(String nombre);
    @Query("SELECT a FROM Autores a WHERE  a.fechaNacimiento > :Year")
    List<Autores> findAutoresByYear(Integer Year);
}
