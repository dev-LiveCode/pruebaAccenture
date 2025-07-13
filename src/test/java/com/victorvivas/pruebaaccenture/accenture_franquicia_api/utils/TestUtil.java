package com.victorvivas.pruebaaccenture.accenture_franquicia_api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {
    
    /**
     * Carga un archivo JSON desde el classpath y lo convierte en una instancia de una clase específica.
     *
     * @param path Ruta relativa al archivo dentro de `src/test/resources`
     * @param type Clase del objeto al que se debe convertir el contenido del JSON
     * @param <T>  Tipo genérico del objeto de retorno
     * @return Instancia del objeto cargado desde el JSON
     * @throws IOException Si hay errores al leer o parsear el archivo
     */
    public static <T> T loadJson(String path, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = new ClassPathResource(path).getInputStream();
        return mapper.readValue(is, type);
    }


    /**
     * Carga un archivo JSON desde el classpath y lo convierte en una lista de objetos de tipo genérico.
     *
     * @param path    Ruta relativa al archivo dentro de `src/test/resources`
     * @param typeRef Referencia de tipo para indicar que el resultado será una lista de objetos
     * @param <T>     Tipo genérico de los objetos dentro de la lista
     * @return Lista de objetos cargados desde el JSON
     * @throws IOException Si hay errores al leer o parsear el archivo
     */
    public static <T> List<T> loadJsonList(String path, TypeReference<List<T>> typeRef) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = new ClassPathResource(path).getInputStream();
        return mapper.readValue(is, typeRef);
    }
}
