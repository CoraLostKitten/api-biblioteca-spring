package com.example.apibiblioteca.repositories;
//paquetes importados
import com.example.apibiblioteca.entities.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long>{
}
