package com.aluracursos.Challenge.LiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibros {
    @JsonAlias("id")
    private Long idLibro;
    @JsonAlias("title")
    private String titulo;
    @JsonAlias("authors")
    private List<DatosAutores> autores;
    @JsonAlias("languages")
    private List<String> idiomas;
    @JsonAlias("download_count")
    private Integer descargas;
    @JsonAlias("books")
    private List<Libros> books;


    public Long getIdLibro() {
        return idLibro; }
    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro; }
    public String getTitulo() {
        return titulo; }
    public void setTitulo(String titulo) {
        this.titulo = titulo; }
    public List<DatosAutores> getAutores() {
        return autores; }
    public void setAutores(List<DatosAutores> autores) {
        this.autores = autores; }
    public List<String> getIdiomas() {
        return idiomas; }
    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas; }
    public Integer getDescargas() {
        return descargas; }
    public void setDescargas(Integer descargas) {
        this.descargas = descargas; }
    public List<Libros> getBooks() {
        return books; }
    public void setBooks(List<Libros> books) {
        this.books = books; }
}