
package com.egg.news.controladores;

import com.egg.news.entidades.Noticia;
import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.NoticiaServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoticiaControlador {
    
    @Controller
    @RequestMapping("/noticia") //localhost:8080/noticia
    public class AutorControlador {
    
        @Autowired
        private NoticiaServicio noticiaServicio;

        @GetMapping("/crear") //localhost:8080/noticia/registrar
        public String crear(){

            return "noticia_form.html";
        }

        @PostMapping("/creando")
        public String creando(@RequestParam String titulo, @RequestParam String cuerpo) throws MiException{

            try {
                noticiaServicio.crearNoticia(titulo, cuerpo);
                return "index.html";
            } catch (MiException ex) {
                Logger.getLogger(NoticiaControlador.class.getName()).log(Level.SEVERE, null, ex);
                return "noticia_form.html";
            }     
        }

        @GetMapping("/lista") //localhost:8080/noticia/lista
        public String listar(ModelMap modelo){

             List<Noticia> noticias = noticiaServicio.listarNoticias();
             modelo.addAttribute("noticias", noticias);
             return "noticias_list.html";
        }

        @GetMapping("/modificar/{id}")
        public String modificar(@PathVariable String id, ModelMap modelo){

            modelo.put("noticia", noticiaServicio.getOne(id));

            return "noticia_modificar.html";
        }

        @PostMapping("/modificar/{id}")
        public String modificar(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo) throws MiException{

            try {
                noticiaServicio.modificarNoticia(id, titulo, cuerpo); 
                return "redirect:/admin";
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());
                return "noticia_modificar.html";
            }
        }
        
        @GetMapping("/eliminar/{id}")
        public String eliminar(@PathVariable String id, ModelMap modelo){
            
            try {
                noticiaServicio.eliminarNoticia(id);
            } catch (MiException ex) {
                modelo.put("error", ex.getMessage());    
            }
           return "redirect:/admin";
        }    
    }
}
