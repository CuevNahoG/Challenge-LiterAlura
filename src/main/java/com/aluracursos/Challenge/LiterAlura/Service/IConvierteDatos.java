package com.aluracursos.Challenge.LiterAlura.Service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);

}
