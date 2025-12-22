package com.example.apibiblioteca.exception;
//paquetes importados
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice//Anotacion para que se sepa que esto es un controlador y va a responder a errores
public class GlobalExceptionHandler {
    //---VALIDACIÓN Y MANEJO DE ERRORES---
    /*Aplicar las validaciones indicadas en las entidades usando @NotNull, @Size, @Min, @Max, etc.
    * 1. INCLUIR EL VALIDATION EN EL POM
    * 2. INCLUIR ANOTACIONES CORRESPONDIENTES EN LAS CLASES
    * 3. HACER LA CLASE DE EXCEPCIONES PARA IMPOLEMENTA EXCEPCIONES GLOBALES Y IMPLEMENTAR LAS EXCEPCIONES LOCALES DESDE CONTROLLERS*/

    // Manejo de errores de validación (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    /*Manejar errores globalmente con @ControllerAdvice y @ExceptionHandler de forma que si no se introducen los campos obligatorios se devuelva información sobre los errores de validación.*/
    // Manejo de excepciones generales (Errores inesperados)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGlobalException(Exception ex) {
        ex.printStackTrace();   //Para ver toda la traza del error en la consola
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("Error",ex.toString()));
    }
    /*Manejar el error de forma “local” cuando se introduzca un isbn que ya exista en la base de datos y devolver un json del tipo “error”:”isbn repetido”.
    ESTO VA EN EL CONTROLLER PARA QUE SEA DE FORMA LOCAL  */
}


