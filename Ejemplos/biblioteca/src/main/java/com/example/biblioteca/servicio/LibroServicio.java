
package com.example.biblioteca.servicio;

import com.example.biblioteca.entidades.Autor;
import com.example.biblioteca.entidades.Editorial;
import com.example.biblioteca.entidades.Libro;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.repositorio.AutorRepositorio;
import com.example.biblioteca.repositorio.EditorialRepositorio;
import com.example.biblioteca.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LibroServicio {
    
    @Autowired  //
    private LibroRepositorio libroRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional // para metodos que generan cambios en la BD
    public void crearLibro(Long isbn, String Titulo, Integer Ejempleres, 
                String idAutor, String idEditorial) throws MiException{
        
        validar(isbn, Titulo, idAutor, idEditorial, Ejempleres);
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(Titulo);
        libro.setEjemplares(Ejempleres);
        
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);        
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        
        return libros;
    }
    
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial,
            Integer ejemplares) throws MiException{
        validar(isbn, titulo, idAutor, idEditorial, ejemplares);
        Optional<Libro> respuesta =  libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor =  autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial =  editorialRepositorio.findById(idEditorial);
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
        
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);  
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);
        }
    }
    private  void validar (Long isbn, String titulo, String idAutor, String idEditorial,
            Integer ejemplares) throws MiException{
        if (isbn == null) {
            throw new MiException("El isbn no puede ser nulo");
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no pueder estar vacio o ser nulo");
        }
        if (ejemplares == null) {
            throw new MiException("El numero de ejemplares no puede ser nulo");
        }
        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("El autor no pueder estar vacio o ser nulo");
        }
         if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("La editorial no pueder estar vacio o ser nulo");
        }
    }
}
