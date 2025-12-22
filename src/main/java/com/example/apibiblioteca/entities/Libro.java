package com.example.apibiblioteca.entities;
//paquetes importados
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

//getter setters y contructores
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//etiqueta para indicar que se trata de una entidad
@Entity
//para elegir nombre de nuestra tabla
@Table(name = "libros")
public class Libro {
    //atributos (campos de la table)
    //PK de la tabla con la etiqueta @Id
    @Id
    //indicamos qque sera un valor numerico autoincremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El título es obligatorio")//recomendable para campos obligatorios de topo String
    @Size(min = 3, message = "El título debe de tener al menos tres caracteres")
    private String titulo;
    @NotBlank(message = "El ISBN es obligatorio")
    @Column(unique = true) //para que un valor sea unico
    private String isbn;
    private LocalDate fechaPublicacion;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    //relaciones
    @OneToMany(mappedBy = "libro",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonIgnoreProperties("libro")
    private Set<Ejemplar> ejemplares = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libros_autores", // Nombre de la tabla intermedia en MySQL
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    @JsonIgnoreProperties("libros") // Evita el bucle infinito con la entidad Autor
    private Set<Autor> autores = new HashSet<>();
}