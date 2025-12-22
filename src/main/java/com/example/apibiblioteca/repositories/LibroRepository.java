package com.example.apibiblioteca.repositories;
//paquetes importados
import com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO;
import com.example.apibiblioteca.entities.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{
    //----IMPLEMENTACION DE CONSULTAS DERIVADAS POR NOMBRE----
    /*Obtener todos los libros que contengan una cadena de texto en el titulo sin importar mayúsculas o minúsculas. No es necesario que el texto coincida completamente*/
    Page<Libro>findByTituloContainingIgnoreCase(String titulo, Pageable pageable); //ponemos list cuando no sabemos si no va a devolver uno solo Optional si sabemos que solo sera uno
    /*Obtener todos los libros publicados en una fecha concreta introducida por parámetro.*/
    Page<Libro> findByFechaPublicacion(LocalDate fechaPublicacion, Pageable pageable);
    //----CONSULTAS PERSONALIZADAS CON @QUERRY Y JPQL----
    /*- Obtener en una única consulta los datos de los libros y el número de ejemplares. La consulta devolverá una colección de LibroYNumEjemplaresDTO.*/
    @Query("SELECT new com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO(" +
            "l.id, l.titulo, l.isbn, l.fechaPublicacion, COUNT(e)) " +
            "FROM Libro l LEFT JOIN l.ejemplares e " +
            "GROUP BY l.id, l.titulo, l.isbn, l.fechaPublicacion")
    Page<LibroYNumEjemplaresDTO> obtenerResumenLibros(Pageable pageable);
    /*- Obtener el número de libros escritos antes del año introducido por parámetro.*/
    @Query("SELECT COUNT(l) FROM Libro l WHERE YEAR(l.fechaPublicacion) < :anio")
    Long countLibrosPublicadosAntesDe(int anio);



}
