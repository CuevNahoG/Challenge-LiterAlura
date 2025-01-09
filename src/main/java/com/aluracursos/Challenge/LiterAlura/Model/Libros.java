package com.aluracursos.Challenge.LiterAlura.Model;

import jakarta.persistence.*;



@Entity
@Table(name ="libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autores autores;
    @Enumerated(EnumType.STRING)
    private CategoriaIdioma idioma;
    @Column(name = "numero_descargas", nullable = false)
    private Integer descargas;

    public Libros(){}

    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.autores = new Autores(datosLibros.autores().get(0));
        this.descargas = datosLibros.descargas() != null ? datosLibros.descargas() : 0; // Valor por defecto
        this.idioma = CategoriaIdioma.fromString(datosLibros.idiomas().toString().split(",")[0].trim());
    }

    @Override
    public String toString() {
        return "-----LIBRO-----\n"+
                "Título: " + titulo + "\n" +
                "Autor: " + autores.getNombre()+ "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de descargas: " + descargas + "\n" +
                "---------------";
    }
    public Long getId() {
        return Id;
    }
    public String getTitulo() {
        return titulo;
    }
    public Autores getAutores() {
        return autores;
    }
    public CategoriaIdioma getIdioma() {
        return idioma;
    }
    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
    public void setId(Long id) {
        Id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutores(Autores autores) {
        this.autores = autores;
    }
    public void setIdioma(CategoriaIdioma idioma) {
        this.idioma = idioma;
    }
}
