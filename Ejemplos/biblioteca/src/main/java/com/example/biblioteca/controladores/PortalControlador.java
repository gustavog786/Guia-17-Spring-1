
package com.example.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador  {
    
    @GetMapping("/") //mapea na url cuando se agrega una barra
    public String index(){
        
        return "index.html";
    }
}
