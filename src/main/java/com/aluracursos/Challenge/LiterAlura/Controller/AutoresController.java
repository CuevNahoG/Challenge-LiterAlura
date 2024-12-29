package com.aluracursos.Challenge.LiterAlura.Controller;

import com.aluracursos.Challenge.LiterAlura.Model.Autores;
import com.aluracursos.Challenge.LiterAlura.Repository.AutoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutoresController {

    @Autowired
    private AutoresRepository autoresRepository;

    @GetMapping
    public List<Autores> obtenerTodosLosAutores() {
        return autoresRepository.findAll();
    }

    @GetMapping("/{nombre}")
    public Autores obtenerAutorPorNombre(@PathVariable String nombre) {
        return autoresRepository.findByNombreIgnoreCase(nombre);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Autores> obtenerAutoresPorFecha(@PathVariable int fecha) {
        return autoresRepository.findAutoresByFechaViva(fecha);
    }
}
