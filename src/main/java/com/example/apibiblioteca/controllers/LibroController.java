package com.example.apibiblioteca.controllers;

import com.example.apibiblioteca.dto.LibroYNumEjemplaresDTO;
import com.example.apibiblioteca.entities.Autor;
import com.example.apibiblioteca.entities.Ejemplar;
import com.example.apibiblioteca.entities.Libro;
import com.example.apibiblioteca.repositories.AutorRepository;
import com.example.apibiblioteca.repositories.EjemplarRepository;
import com.example.apibiblioteca.repositories.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository; // Necesario para punto 8 y 9

    @Autowired
    private EjemplarRepository ejemplarRepository; // Necesario para punto 10

    // 1. GET (listar libros paginados y ordenados)
    @GetMapping
    public ResponseEntity<Page<Libro>> findAllLibros(
            @PageableDefault(page = 0, size = 5, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(libroRepository.findAll(pageable));
    }

    // 2. GET resumen (DTO)
    @GetMapping("/resumen")
    public ResponseEntity<Page<LibroYNumEjemplaresDTO>> getResumen(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<LibroYNumEjemplaresDTO> resumen = libroRepository.obtenerResumenLibros(pageable);
        return resumen.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resumen);
    }

    // 3. POST /libros
    @PostMapping
    public ResponseEntity<Libro> createLibro(@RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroRepository.save(libro));
    }

    // 4. GET /libros/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Libro> findLibro(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. DELETE /libros/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 6. GET /libros/buscar
    @GetMapping("/buscar")
    public ResponseEntity<Page<Libro>> buscarPorTitulo(
            @RequestParam String titulo,
            Pageable pageable) {
        Page<Libro> libros = libroRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return libros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(libros);
    }

    // 7. GET /libros/por-fecha
    @GetMapping("/filtro-fecha/{fecha}")
    public ResponseEntity<Page<Libro>> buscarPorFecha(
            @PathVariable LocalDate fecha,
            Pageable pageable) {

        Page<Libro> libros = libroRepository.findByFechaPublicacion(fecha, pageable);
        return ResponseEntity.ok(libros);
    }

    // 8. POST añadir autor
    @PostMapping("/{libroId}/autores/{autorId}")
    @Transactional
    public ResponseEntity<Libro> añadirAutor(@PathVariable Long libroId, @PathVariable Long autorId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));

        libro.getAutores().add(autor);
        return ResponseEntity.ok(libroRepository.save(libro));
    }

    // 9. DELETE quitar autor
    @DeleteMapping("/{libroId}/autores/{autorId}")
    @Transactional
    public ResponseEntity<Void> quitarAutor(@PathVariable Long libroId, @PathVariable Long autorId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));

        if (libro.getAutores().remove(autor)) {
            libroRepository.save(libro);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // 10. POST crear ejemplar
    @PostMapping("/{id}/ejemplares")
    public ResponseEntity<Ejemplar> crearEjemplar(@PathVariable Long id, @RequestBody Ejemplar nuevoEjemplar) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        nuevoEjemplar.setLibro(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(ejemplarRepository.save(nuevoEjemplar));
    }

    // 11. DELETE borrar ejemplar (Orphan Removal)
    @DeleteMapping("/{libroId}/ejemplares/{ejemplarId}")
    @Transactional
    public ResponseEntity<Void> borrarEjemplar(@PathVariable Long libroId, @PathVariable Long ejemplarId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));

        if (libro.getEjemplares().removeIf(e -> e.getId().equals(ejemplarId))) {
            libroRepository.save(libro);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

