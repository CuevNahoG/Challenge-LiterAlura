package com.aluracursos.Challenge.LiterAlura.Principal;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;

public class BuscarLibroException extends Throwable {
    public BuscarLibroException(String s, RuntimeException e) {
    }

    public BuscarLibroException(String s, IOException e) {
    }

    public BuscarLibroException(String s, JsonParseException e) {
    }
}
