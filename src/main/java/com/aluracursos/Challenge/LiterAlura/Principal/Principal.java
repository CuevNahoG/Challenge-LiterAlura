package com.aluracursos.Challenge.LiterAlura.Principal;

import com.aluracursos.Challenge.LiterAlura.Model.Autores;
import com.aluracursos.Challenge.LiterAlura.Model.DatosLibros;
import com.aluracursos.Challenge.LiterAlura.Model.Libros;
import com.aluracursos.Challenge.LiterAlura.Repository.AutoresRepository;
import com.aluracursos.Challenge.LiterAlura.Repository.LibrosRepository;
import com.aluracursos.Challenge.LiterAlura.Service.ConsumoApi;
import com.aluracursos.Challenge.LiterAlura.Service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

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

    public void muestraElMenu() throws BuscarLibroException, IOException {
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

    private DatosLibros BuscarLibrosPorTitulo() throws BuscarLibroException {
        try {
            System.out.print("Introduce el título del libro que deseas buscar: ");
            var nombreLibros = teclado.nextLine();
            var url = URL_BASE + "?search=" + nombreLibros.replace(" ", "%20");
            String json = consumoApi.obtenerDatos(url);
            DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
            return datos;
        } catch (Exception e) {
            String mensajeError;
            if (e instanceof IOException) {
                mensajeError = "Error de conexión o de entrada/salida: " + e.getMessage();
            } else if (e instanceof JsonParseException) {
                mensajeError = "Error al procesar la respuesta JSON: " + e.getMessage();
            } else {
                mensajeError = "Error inesperado al buscar el libro: " + e.getMessage();
            }
            System.err.println(mensajeError);
            throw new BuscarLibroException("Error al buscar el libro: " + mensajeError, (RuntimeException) e);
        }
    }
    private void MostrarLibrosRegistrados(){
        List<Libros> libros = librosRepository.findAll();
        System.out.println("Estos son los libros registrados en la base de datos hasta ahora : " + libros);
    }

    private void MostrarAutoresRegistrados() {
        List<Autores> autores = autoresRepository.findAll();
        System.out.println("Estos son loa autores registrados en la base de datos hasta el momento: " + autores);
    }

    private void BuscarAutoresPorFecha() {
        System.out.println("Escribe la fecha que deseas buscar  (por ejemplo, 1800): ");
        String inputFecha = teclado.nextLine();
        int fecha;
        try {
            fecha = Integer.parseInt(inputFecha);
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido. Por favor ingresa un año válido.");
            return;
        }
        List<Autores> autores = autoresRepository.findAutoresByFechaViva(fecha);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores que vivieron en ese año");
        }else {
            System.out.println("Estos son los autores que vivieron en" + fecha + ": ");
            for (Autores autor: autores){
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                if (autor.getFechaDeceso() == 0) {
                    System.out.println("Fecha de deceso: Aún vive.");
                } else {
                    System.out.println("Fecha de deceso: " + autor.getFechaDeceso());
                }
                System.out.println("-------------------------------");
            }
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




