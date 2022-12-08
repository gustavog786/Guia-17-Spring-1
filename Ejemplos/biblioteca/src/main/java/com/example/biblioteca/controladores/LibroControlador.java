
package com.example.biblioteca.controladores;

import com.example.biblioteca.entidades.Autor;
import com.example.biblioteca.entidades.Editorial;
import com.example.biblioteca.entidades.Libro;
import com.example.biblioteca.excepciones.MiException;
import com.example.biblioteca.servicio.AutorServicio;
import com.example.biblioteca.servicio.EditorialServicio;
import com.example.biblioteca.servicio.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/libro") //localhost:8080/libro
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio; 
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);  
        
        return "libro_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required=false) Long isbn, @RequestParam String titulo,
            @RequestParam(required=false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo){
        
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial); //si todo sale bien retornamos al index
            modelo.put("exito", "El libro fue cargado exitosamente");
        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());
            
            return "libro_form.html";  //volvemoa a cargar el formulario
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
    
         List<Libro> libros = libroServicio.listarLibros();
         modelo.addAttribute("libros", libros);
         
         return "libro_list.html";
    }
}
