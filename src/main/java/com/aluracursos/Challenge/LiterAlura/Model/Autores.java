package com.aluracursos.Challenge.LiterAlura.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autores")
public class Autores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int fechaNacimiento;
    private int fechaDeceso;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libros> libros = new ArrayList<>();

    public Autores(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombreAutor();
        this.fechaNacimiento = datosAutores.fechaNacimiento();
        this.fechaDeceso = datosAutores.fechaDeceso();
    }

    public Autores() {}

    public Autores(String nombreAutor) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaDeceso() {
        return fechaDeceso;
    }

    public void setFechaDeceso(int fechaDeceso) {
        this.fechaDeceso = fechaDeceso;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }
}
