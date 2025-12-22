package com.example.apibiblioteca.controllers;
//importar paquetes necesarios


import com.example.apibiblioteca.entities.Ejemplar;
import com.example.apibiblioteca.entities.Libro;
import com.example.apibiblioteca.repositories.AutorRepository;
import com.example.apibiblioteca.repositories.EjemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/ejemplares") //etiqueta para establecer la ruta base api en todas las funciones del controller
public class EjemplarController {

    @Autowired
    EjemplarRepository ejemplarRepository;


    //getmappin basico para comprobar postman
    @GetMapping //para que cuando en la url salga esto nos devuelva una lista de ejemplares
    public ResponseEntity<List<Ejemplar>> findAllEjemplares() { //uso de response entity para dar una respuesta con el codigo correcto en este caso 200 q es OK
        return ResponseEntity.ok(ejemplarRepository.findAll());
    }
}
