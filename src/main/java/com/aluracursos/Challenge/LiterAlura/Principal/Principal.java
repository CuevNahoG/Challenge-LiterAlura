package com.aluracursos.Challenge.LiterAlura.Principal;

import com.aluracursos.Challenge.LiterAlura.Model.Autores;
import com.aluracursos.Challenge.LiterAlura.Model.DatosLibros;
import com.aluracursos.Challenge.LiterAlura.Model.Libros;
import com.aluracursos.Challenge.LiterAlura.Repository.AutoresRepository;
import com.aluracursos.Challenge.LiterAlura.Repository.LibrosRepository;
import com.aluracursos.Challenge.LiterAlura.Service.ConsumoApi;
import com.aluracursos.Challenge.LiterAlura.Service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibrosRepository librosRepository;
    private AutoresRepository autoresRepository;
    private List<Libros> libros;
    private List<Autores> autores;

    @Autowired
    public Principal(LibrosRepository librosRepository, AutoresRepository autoresRepository){
        this.librosRepository = librosRepository;
        this.autoresRepository = autoresRepository;
    }

    public void muestraElMenu(){
        var opcion = -1;
        System.out.println("Bienvenido al buscador de libros LiterAlura! \n" +
                "Por favor selecciona una opción: ");
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por título
                    2 - Mostrar libros registrados 
                    3 - Mostrar autores registrados
                    4 - Buscar autores en un año determinado 
                    5 - Buscar libros por idioma
                    0 - | Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    BuscarLibrosPorTitulo();
                    break;
                case 2:
                    MostrarLibrosRegistrados();
                    break;
                case 3:
                    MostrarAutoresRegistrados();
                    break;
                case 4:
                    BuscarAutoresPorFecha();
                    break;
                case 5:
                    BuscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando aplicación...");
                    break;

                default:
                    System.out.println("Opción no válida, intenta de nuevo");
            }
        }
    }

    private void BuscarLibrosPorTitulo() {
        try {
            System.out.print("Introduce el nombre del libro que deseas buscar: ");
            String nombreLibros = teclado.nextLine().toLowerCase();

            String url = URL_BASE + "?search=" + nombreLibros.replace(" ", "%20");
            String json = consumoApi.obtenerDatos(url);
            DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);

            if (datos.getBooks().isEmpty()) {
                System.out.println("No se encontraron libros que coincidan con el título ingresado.");
                return;
            }

            System.out.println("\n----- Resultados de la búsqueda -----");
            for (Libros libroApi : datos.getBooks()) {
                Libros libroBd = librosRepository.findByTitulo(libroApi.getTitulo());

                if (libroBd == null) {
                    Libros nuevoLibro = new Libros();
                    nuevoLibro.setTitulo(libroApi.getTitulo());
                    nuevoLibro.setIdioma(libroApi.getIdioma());
                    nuevoLibro.setAutores(mapearAutores(libroApi.getAutores()));

                    librosRepository.save(nuevoLibro);
                    System.out.println("Libro '" + nuevoLibro.getTitulo() + "' añadido a la base de datos.");
                } else {
                    System.out.println("Libro '" + libroBd.getTitulo() + "' ya existe en la base de datos.");
                }

                System.out.println("Título: " + libroApi.getTitulo());
                System.out.println("Autores: " + libroApi.getAutores().stream().map(Autores::getNombre).collect(Collectors.joining(", ")));
                System.out.println("Idioma: " + libroApi.getIdioma());
                System.out.println("-------------------------------------");
            }
        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error al realizar la búsqueda: " + e.getMessage());
        }
    }

    private List<Autores> mapearAutores(List<Autores> autoresApi) {
        return autoresApi.stream()
                .map(autorApi -> {
                    Autores autorBd = autoresRepository.findByNombreIgnoreCase(autorApi.getNombre());
                    if (autorBd == null) {
                        autorBd = new Autores(autorApi.getNombre());
                        autoresRepository.save(autorBd);
                    }
                    return autorBd;
                })
                .collect(Collectors.toList());
    }

    private void MostrarLibrosRegistrados(){
        List<Libros> librosRegistrados = librosRepository.findAll();

        if (librosRegistrados.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }
        System.out.println("\n----- Libros registrados en la base de datos -----\n");

        for (Libros libro : librosRegistrados) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutores());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("---------------------------------------------------");
        }
    }

    private void MostrarAutoresRegistrados() {
        List<Autores> autoresRegistrados = autoresRepository.findAll();

        if (autoresRegistrados.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        System.out.println("\n----- AUTORES REGISTRADOS EN LA BASE DE DATOS -----\n");

        for (Autores autor : autoresRegistrados) {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
            if (autor.getFechaDeceso() == 0) {
                System.out.println("Estado: Aún vive");
            } else {
                System.out.println("Fecha de Deceso: " + autor.getFechaDeceso());
            }
            System.out.println("---------------------------------------------------");
        }
    }

    private void BuscarAutoresPorFecha() {
        System.out.println("Escribe el año que deseas buscar (por ejemplo, 1800): ");
        String inputAnio = teclado.nextLine();

        try {
            int anio = Integer.parseInt(inputAnio);
            List<Autores> autoresEncontrados = autoresRepository.findAutoresByFechaViva(anio);

            if (autoresEncontrados.isEmpty()) {
                System.out.println("No se encontraron autores para el año " + anio + ".");
            } else {
                System.out.println("\n----- Autores vivos o que vivieron en " + anio + " -----");
                for (Autores autor : autoresEncontrados) {
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                    if (autor.getFechaDeceso() == 0) {
                        System.out.println("Estado: Aún vive");
                    } else {
                        System.out.println("Fecha de Deceso: " + autor.getFechaDeceso());
                    }
                    System.out.println("---------------------------------------------------");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido. Por favor ingresa un año válido.");
        }
    }

    private void BuscarLibrosPorIdioma () {
        System.out.println("Escribe el idioma por el que deseas buscar: ");
        String menu = """
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """;
        System.out.println(menu);
        var idioma = teclado.nextLine();
        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
            System.out.println("Idioma no válido, intenta de nuevo");
            return;
        }
        List<Libros> librosPorIdioma = librosRepository.findByIdiomaContaining(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma");
            return;
        }
        System.out.println("----- LOS LIBROS REGISTRADOS EN EL IDIOMA SELECCIONADO SON: -----\n");
        librosPorIdioma.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);

    }

}




