package com.example.apibiblioteca.controllers;
//importar paquetes necesarios


import com.example.apibiblioteca.entities.Autor;
import com.example.apibiblioteca.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController //indicar que es un controller
@RequestMapping("/api/autores") //etiqueta para establecer la ruta base api en todas las funciones del controller
public class AutorController {

    //crear repositorio empleados y con la etiqueta Autowired spring se encarga de inicializarlo y gestionarlo todo el tiempo necesario
    @Autowired
    AutorRepository autorRepository;

    @GetMapping //para que en la utl salgan los autores
    //codigo para que esto ocurra
    public ResponseEntity<List<Autor>> findAllAutores() { //uso de response entity para dar una respuesta con el codigo correcto en este caso 200 q es OK
        return ResponseEntity.ok(autorRepository.findAll());
    }

    // Crear un autor nuevo (necesario para tener autores en la BD)
    @PostMapping
    public ResponseEntity<Autor> create(@RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorRepository.save(autor));
    }
}
