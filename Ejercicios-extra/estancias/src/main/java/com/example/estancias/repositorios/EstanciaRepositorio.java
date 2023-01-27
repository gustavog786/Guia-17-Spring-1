
package com.example.estancias.repositorios;

import com.example.estancias.entidades.Estancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EstanciaRepositorio extends JpaRepository<Estancia, String>{
    
    
}
