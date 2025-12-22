package com.example.apibiblioteca.dto;
//paquetes importados
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroYNumEjemplaresDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private LocalDate fechaPublicacion;
    private Long numEjemplares; //resulotado de COUNT
}
