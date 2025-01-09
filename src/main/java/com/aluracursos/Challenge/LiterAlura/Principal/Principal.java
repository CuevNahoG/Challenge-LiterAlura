package com.aluracursos.Challenge.LiterAlura.Principal;

import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;
import com.aluracursos.Challenge.LiterAlura.Model.*;
import com.aluracursos.Challenge.LiterAlura.Repository.AutoresRepository;
import com.aluracursos.Challenge.LiterAlura.Repository.LibrosRepository;


import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoAPI = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<Libros> libros;
    private List<Autores> autores;
    private LibrosRepository librosRepository;
    private AutoresRepository autoresRepository;


    public Principal(AutoresRepository autorRepository, LibrosRepository libroRepository){
        this.librosRepository = libroRepository;
        this.autoresRepository = autorRepository;
    }
    //Menu de opciones
    public void muestramenu(){
        int opcion = -1;
        String menu = """
                   Bienvenido/a al Buscador de Libros
          1) Buscar libro por título 
          2) Listar libros registrados
          3) Listar autores registrados
          4) Listar autores vivos en un determinado año
          5) Listar libros por idioma
          
          0) Salir
          ***************************************************       
          """;


        while (opcion != 0) {
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del 0 al 5.");
                teclado.nextLine();
                continue;
            }
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorYear();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.printf("Opción inválidaPor favor, ingrese un número del 0 al 5.\n");
            }
        }

    }

    private void listarLibrosPorIdioma() {
        String menuIdioma = """
                Ingrese el idioma para buscar los libros: 
                es >> Español
                en >> Ingles
                fr >> Frances 
                pt >> Portugues
                """;

        System.out.println(menuIdioma);
        String idiomaBuscado = teclado.nextLine();
        CategoriaIdioma idioma = null;

        switch (idiomaBuscado){
            case "es":
                idioma = CategoriaIdioma.fromEspanol("Español") ;
                break;
            case "en":
                idioma = CategoriaIdioma.fromEspanol("Ingles") ;
                break;
            case "fr":
                idioma = CategoriaIdioma.fromEspanol("Frances") ;
                break;
            case "pt":
                idioma = CategoriaIdioma.fromEspanol("Portugues");
                break;
            default:
                System.out.println("Entrada inválida.");
                return;

        }
        buscarPorIdioma(idioma);

    }

    private void buscarPorIdioma(CategoriaIdioma idioma){
        libros = librosRepository.findLibrosByidioma(idioma);
        if (libros.isEmpty()){
            System.out.println("No hay libros registrados");
        } else {
            libros.stream().forEach(System.out::println);
        }
    }

    private void listarAutoresPorYear() {

        System.out.println("Ingrese el año vivo de Autore(s) que desea buscar: ");
        try {
            Integer year = teclado.nextInt();
            autores = autoresRepository.findAutoresByYear(year);
            if (autores.isEmpty()){
                System.out.println("No hay autores en ese rango");
            } else {
                autores.stream().forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un año correcto");
            teclado.nextLine();
        }

    }


    private void listarAutoresRegistrados() {

        autores = autoresRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    private void listarLibrosRegistrados() {
        libros = librosRepository.findAll();
        libros.stream().forEach(System.out::println);

    }

    private String realizarConsulta (){
        //System.out.println("Opcion 1");
        System.out.println("Escribe el nombre del libro a buscar: ");
        var nombreLibro = teclado.nextLine();
        //String url= "https://gutendex.com/books/?search=Quijote";
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "%20");
        System.out.println("Esperando la respuesta...");
        String respuesta = consumoAPI.obtenerDatosApi(url);
        return respuesta;
    }

    private void buscarLibroPorTitulo() {
        String respuesta = realizarConsulta();
        DatosConsultaApi datosConsultaAPI =convierteDatos.obtenerDatos(respuesta, DatosConsultaApi.class);
        if (datosConsultaAPI.numeroLibros() !=0) {
            DatosLibros primerLibro = datosConsultaAPI.resultado().get(0);
            Autores autoresLibros = new Autores(primerLibro.autores().get(0));
            Optional<Libros> libroBase = librosRepository.findLibroBytitulo(primerLibro.titulo());
            if (libroBase.isPresent()) {
                System.out.println("No se puede registrar el mismo líbro ");
            } else {
                Optional<Autores> autorDeBase = autoresRepository.findBynombre(autoresLibros.getNombre());
                if (autorDeBase.isPresent()) {
                    autoresLibros = autorDeBase.get();
                } else {
                    autoresRepository.save(autoresLibros);
                }

                Libros libro = new Libros(primerLibro);
                libro.setAutores(autoresLibros);
                librosRepository.save(libro);
                System.out.println(libro);
            }
        } else {
            System.out.println("Líbro no encontrado...");
        }
    }


}
