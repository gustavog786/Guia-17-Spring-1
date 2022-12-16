
package com.egg.news.controladores;

import com.egg.news.entidades.Noticia;
import com.egg.news.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin") //localhost:8080/admin
public class PanelAdminControlador {
    
    @Autowired
    NoticiaServicio noticiaServicio;
    

    @GetMapping("") //localhost:8080/panelAdmin
        public String index(ModelMap modelo){

             List<Noticia> noticias = noticiaServicio.listarNoticias();
             modelo.addAttribute("noticias", noticias);
             return "panelAdmin.html";
        }
}
