package com.aluracursos.Challenge.LiterAlura.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String idioma;

    private Integer descargas;

    @ManyToMany
    @JoinTable(
            name = "libro_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )

    private List<Autores> autores;

    // Constructor vacío requerido por JPA
    public Libros() {
    }

    public Libros(String titulo, String idioma, String nombreAutor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.autores = List.of(new Autores(nombreAutor));
    }


    @Override
    public String toString() {
        String numeroDescargas = "0";
        return "Libros{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", descargas=" + descargas +
                ", numeroDescargas=" + numeroDescargas +
                ", autores=" + autores +
                '}';
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    public void setAutores(List<Autores> autores) {
        this.autores = autores;
    }
}
