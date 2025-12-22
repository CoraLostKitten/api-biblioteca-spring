package com.example.apibiblioteca.entities;
//paquete importados
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

//getter setters y contructores
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//etiquete para indicar que se trata de una entidad
@Entity
//para elegir nombre de nuestra table
@Table(name = "autores")

public class Autor {
    //atributos (campos de la table)
    //PK de la tabla con la etiqueta @Id
    @Id
    //indicamos qque sera un valor numerico autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, message = "El nombre debe de tener al menos tres caracteres")
    private String nombre;
    //relaciones
    @ManyToMany(mappedBy = "autores")
    @JsonIgnoreProperties("autores") // CORRECCIÓN 3: Evita el bucle infinito en Postman
    private Set<Libro> libros = new HashSet<>();
}
