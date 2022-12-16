
package com.egg.news.repositorios;

import com.egg.news.entidades.Noticia;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String>{
    
    @Query("SELECT n FROM Noticia n WHERE n.titulo = :titulo ORDER BY n.alta DESC")
    public ArrayList<Noticia>todosDescendente();

}
