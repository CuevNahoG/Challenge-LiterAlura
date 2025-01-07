package com.aluracursos.Challenge.LiterAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosConsultaApi(
        @JsonAlias("count") Integer numeroLibros,
        @JsonAlias("next") String pagProxima,
        @JsonAlias("previous") String pagAnterior,
        @JsonAlias("results") List<DatosLibros> resultado){

}
