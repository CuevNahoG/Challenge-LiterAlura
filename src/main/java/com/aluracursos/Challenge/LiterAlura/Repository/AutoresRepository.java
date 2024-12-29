package com.aluracursos.Challenge.LiterAlura.Repository;

import com.aluracursos.Challenge.LiterAlura.Model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutoresRepository extends JpaRepository<Autores, Long> {
    Autores findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autores a WHERE " +
            "(:anio BETWEEN a.fechaNacimiento AND a.fechaDeceso) OR " +
            "(a.fechaDeceso = 0 AND a.fechaNacimiento <= :anio)")
    List<Autores> findAutoresByFechaViva(@Param("anio") int anio);
}
