/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.estancias.controladores;

import com.example.estancias.excepciones.MiException;
import com.example.estancias.servicios.FamiliaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/familias")
public class FamiliaController {
    
    @Autowired
    FamiliaServicio familiaServicio;
    
    @GetMapping("/")
    public String index(){
        return "index_familia.html";
    }
    
    @GetMapping("/registro")
    public String registro(){
        return "registro_familia.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String clave1, @RequestParam String clave2,
            ModelMap modelo) {
        try {

            familiaServicio.crearFamilia(nombre, email, clave1, clave2);

            modelo.put("exito", "La familia fue creada correctamente!");

            return "index.html";
        } catch ( MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);    ///recuerda los valores ingresados para no volverlos a cargar
            modelo.put("email", email);

            return "redirect:/registro";
        }
    }
    
    
}
