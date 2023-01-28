/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.estancias.controladores;

import com.example.estancias.entidades.Usuario;
import com.example.estancias.excepciones.MiException;
import com.example.estancias.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("")
    public String index(){
        return "index_usuarios.html";
    }
    
    @GetMapping("/registro")
    public String registro(){
        return "registro_usuarios.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(@RequestParam String alias, @RequestParam String email,
            @RequestParam String clave1, @RequestParam String clave2,
            ModelMap modelo) {
        try {

            usuarioServicio.crearUsuario(alias, email, clave1, clave2);

            modelo.put("exito", "El usuario fue creado correctamente!");

            return "index.html";
        } catch ( MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", alias);    ///recuerda los valores ingresados para no volverlos a cargar
            modelo.put("email", email);

            return "redirect:/registro";
        }  
    }
    
     @GetMapping("/lista")
    public String listar(ModelMap modelo){
    
         List<Usuario> usuarios = usuarioServicio.listarUsuarios();
         modelo.addAttribute("usuarios", usuarios );
         
         return "usuarios_list.html";
    }
}
