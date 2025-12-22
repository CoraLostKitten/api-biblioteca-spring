package com.example.apibiblioteca.entities;
//importaciones
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//getter setters y contructores
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//etiquete para indicar que se trata de una entidad
@Entity
//para elegir nombre de nuestra table
@Table(name = "ejemplares")
public class Ejemplar {
    //atributos (campos de la table)
    //PK de la tabla con la etiqueta @Id
    @Id
    //indicamos qque sera un valor numerico autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String descripcion;
    @NotNull(message = "Debes indicar si el ejemplar está disponible")
    private Boolean disponible;
    //relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "libro_id", nullable = false)
    @JsonIgnoreProperties("ejemplares")
    private Libro libro;

}
