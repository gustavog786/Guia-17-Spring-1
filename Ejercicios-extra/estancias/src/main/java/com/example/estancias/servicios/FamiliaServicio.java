/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.estancias.servicios;

import com.example.estancias.entidades.Casa;
import com.example.estancias.entidades.Cliente;
import com.example.estancias.entidades.Estancia;
import com.example.estancias.entidades.Familia;
import com.example.estancias.enumeraciones.Rol;
import com.example.estancias.excepciones.MiException;
import com.example.estancias.repositorios.FamiliaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FamiliaServicio {
    
    @Autowired
    FamiliaRepositorio familiaRepositorio;

    @Autowired
    ValidacionServicio validacionServicio;
    
    public void crearFamilia(String nombre, String email, String clave1, String clave2) throws MiException{
        
        validacionServicio.validarNombre(nombre);
        validacionServicio.validarEmail(email);
        validacionServicio.validarPassword(clave1);
        validacionServicio.validarPassword2(clave1,clave2);
        
        Familia familia = new Familia();
        
        //seteo los parametros
        familia.setNombre(nombre);
        familia.setEmail(email);
        familia.setClave(new BCryptPasswordEncoder().encode(clave1));
        familia.setRol(Rol.USER);
        
        //persisto - guardo en BD
        familiaRepositorio.save(familia);
    }
    
     //Metodo listar familias
    public List<Familia> listarFamilias() {

        List<Familia> listaFamilias = new ArrayList<>();

        // Encuentra reservas dentro de repositorio, los mete en arraylist listaReservas
        listaFamilias = familiaRepositorio.findAll();

        return listaFamilias;
    }
    
    //Metodo modificar familia
    public void modificarFamilia(String nombre, String email, String clave1, String clave2) throws MiException{
        
        validacionServicio.validarNombre(nombre);
        validacionServicio.validarEmail(email);
        validacionServicio.validarPassword(clave1);
        validacionServicio.validarPassword2(clave1,clave2);
        
        Familia familia = new Familia();
        
        //seteo los parametros
        familia.setNombre(nombre);
        familia.setEmail(email);
        familia.setClave(clave1);
        familia.setClave(new BCryptPasswordEncoder().encode(clave1));
        familia.setRol(Rol.USER);
        //persisto - guardo en BD
        familiaRepositorio.save(familia);
    }
    
    //metodo eliminar Familias
    @Transactional
    public void eliminarFamilias( String id) throws MiException{
        Familia familia = familiaRepositorio.getById(id);
        familiaRepositorio.delete(familia);
    }
}
